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

package com.asialjim.clinc.service;

import com.asialjim.clinc.api.PrescriptionRecordApi;
import com.asialjim.clinc.service.prescription.BasePrescriptionReminder;
import com.asialjim.clinc.vo.PrescriptionRecordVo;
import com.asialjim.clinc.vo.PrescriptionRemindVo;
import com.asialjim.microapplet.common.page.PageData;
import com.asialjim.microapplet.common.security.MamsSession;
import com.asialjim.microapplet.common.security.MamsSessionAttribute;
import com.asialjim.microapplet.commons.security.AuthorityRes;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * 提示器服务
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/10/22, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Service
@RequiredArgsConstructor
public class PrescriptionRemindService {
    private final MamsSessionAttribute mamsSessionAttribute;
    private final List<BasePrescriptionReminder> reminders;
    private final PrescriptionRecordApi prescriptionRecordApi;

    @PostConstruct
    public void init() {
        this.reminders.sort(BasePrescriptionReminder::compareTo);
    }


    public PrescriptionRecordVo queryById(String id) {
        PrescriptionRecordVo vo = this.prescriptionRecordApi.queryById(id);
        if (Objects.nonNull(vo)) {
            MamsSession mamsSession = this.mamsSessionAttribute.currentLoginSession();
            if (!StringUtils.equals(mamsSession.getUserid(), vo.getUserid()))
                AuthorityRes.NoPermission.thr();
        }
        return vo;
    }

    public PageData<PrescriptionRemindVo> query(
            Long page, Long size,
            String name, String idNo, String phone) {

        MamsSession mamsSession = this.mamsSessionAttribute.currentLoginSession();
        long roleBit = mamsSession.getRoleBit();
        for (BasePrescriptionReminder reminder : this.reminders) {
            if (!reminder.support(roleBit))
                continue;
            return reminder.query(page, size, name, idNo, phone);
        }

        return new PageData<>(Collections.emptyList());
    }
}