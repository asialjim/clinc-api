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

package com.asialjim.clinc.infrastructure.datasource;

import com.asialjim.clinc.infrastructure.datasource.po.PrescriptionRecordPo;
import com.asialjim.clinc.infrastructure.datasource.service.PrescriptionRecordMapperService;
import com.asialjim.clinc.vo.PrescriptionRecordVo;
import com.asialjim.clinc.vo.QueryLastPrescriptionRecordReq;
import com.asialjim.microapplet.common.page.PageData;
import com.asialjim.microapplet.common.page.PageParameter;
import com.asialjim.microapplet.common.page.Pageable;
import com.asialjim.microapplet.mybatis.flex.page.MyBatisFlexPageFun;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryChain;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;


@Component
@RequiredArgsConstructor
public class PrescriptionRecordRepository {
    private final PrescriptionRecordMapperService prescriptionRecordMapperService;



    public PrescriptionRecordVo queryById(String id) {
        PrescriptionRecordPo  po = this.prescriptionRecordMapperService.queryById(id);
        return PrescriptionRecordPo.toVo(po);
    }

    public PageData<PrescriptionRecordVo> queryLastRecord(Long page, Long size, QueryLastPrescriptionRecordReq req) {
        Page<PrescriptionRecordPo> pageCondition = Pageable.ofPage(PageParameter.pageOf(page, size), MyBatisFlexPageFun.of());
        QueryChain<PrescriptionRecordPo> chain = this.prescriptionRecordMapperService.queryChain();

        if (Objects.nonNull(req)) {
            List<String> useridList = req.getUseridList();
            if (CollectionUtils.isNotEmpty(useridList))
                chain.where(PrescriptionRecordPo::getUserid).in(new HashSet<>(useridList));

            List<String> doctorIdList = req.getDoctorIdList();
            if (CollectionUtils.isNotEmpty(doctorIdList))
                chain.where(PrescriptionRecordPo::getDoctorId).in(new HashSet<>(doctorIdList));

            List<String> nurseIdList = req.getNurseIdList();
            if (CollectionUtils.isNotEmpty(nurseIdList))
                chain.where(PrescriptionRecordPo::getNurseId).in(new HashSet<>(nurseIdList));
        }


        Page<PrescriptionRecordPo> result = chain.page(pageCondition);
        PageData<PrescriptionRecordPo> data = new PageData<>(result.getRecords());
        data.setPage(result.getPageNumber());
        data.setSize(result.getPageSize());
        data.setPages(result.getTotalPage());
        data.setTotal(result.getTotalRow());

        return PageData.of(data, PrescriptionRecordPo::toVo);
    }

}
