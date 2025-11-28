/*
 *    Copyright 2014-2025 <a href="mailto:asialjim@qq.com">Asial Jim</a>
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.asialjim.clinc.service.prescription;

import com.asialjim.clinc.api.PrescriptionRecordApi;
import com.asialjim.clinc.vo.PrescriptionRecordVo;
import com.asialjim.clinc.vo.PrescriptionRemindVo;
import com.asialjim.clinc.vo.QueryLastPrescriptionRecordReq;
import com.asialjim.microapplet.common.page.PageData;
import com.asialjim.microapplet.common.security.MamsSession;
import com.asialjim.microapplet.common.security.MamsSessionAttribute;
import com.asialjim.microapplet.commons.security.RoleCode;
import com.asialjim.microapplet.mams.user.api.UserApi;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * 用药提醒
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/10/22, &nbsp;&nbsp; <em>version:1.0</em>
 */
@RequiredArgsConstructor
public abstract class BasePrescriptionReminder implements Comparable<BasePrescriptionReminder> {
    protected final MamsSessionAttribute mamsSessionAttribute;
    protected final PrescriptionRecordApi prescriptionRecordApi;
    protected final UserApi userApi;

    public boolean support(long role) {
        return RoleCode.contains(role, roleBit());
    }

    public abstract long roleBit();

    @Override
    public final int compareTo(BasePrescriptionReminder o) {
        //noinspection ComparatorMethodParameterNotUsed
        return (o.roleBit() > this.roleBit()) ? 1 : -1;
    }

    protected abstract void beforeQuery(
            String name, String idNo, String phone,
            List<String> useridList,
            List<String> doctorList,
            List<String> nurseList
    );

    public PageData<PrescriptionRemindVo> query(Long page, Long size, String name, String idNo, String phone) {
        final List<String> useridList = new ArrayList<>();
        final List<String> doctorList = new ArrayList<>();
        final List<String> nurseList = new ArrayList<>();
        beforeQuery(name, idNo, phone, useridList, doctorList, nurseList);


        final QueryLastPrescriptionRecordReq req = new QueryLastPrescriptionRecordReq();
        req.setUseridList(useridList);
        req.setDoctorIdList(doctorList);
        req.setNurseIdList(nurseList);

        final PageData<PrescriptionRecordVo> records = this.prescriptionRecordApi.queryLastRecord(page, size, req);

        final MamsSession currentLoginSession = this.mamsSessionAttribute.currentLoginSession();

        return PageData.of(records, item -> {
            PrescriptionRemindVo vo = new PrescriptionRemindVo();
            vo.setId(item.getId());
            vo.setLastDate(item.getVisitDate());
            vo.setLastDays(item.getPreDays());
            vo.setNextDate(item.getNextVisitDate());
            String userid = item.getUserid();
            final MamsSession mamsSession = new MamsSession();
            mamsSession.setUserid(userid);
            mamsSession.setChl(currentLoginSession.getChl());
            mamsSession.setAppid(currentLoginSession.getAppid());
            mamsSession.setChlAppid(currentLoginSession.getChlAppid());
            mamsSession.setChlAppType(currentLoginSession.getChlAppType());
            String userPhone = this.userApi.userPhone(mamsSession);
            vo.setPhone(userPhone);
            return vo;
        });
    }
}