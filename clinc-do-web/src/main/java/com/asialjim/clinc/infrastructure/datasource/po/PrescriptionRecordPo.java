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

package com.asialjim.clinc.infrastructure.datasource.po;

import com.asialjim.clinc.vo.PrescriptionRecordVo;
import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import com.mybatisflex.core.keygen.KeyGenerators;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * 开药记录
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/10/22, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Data
@Table("prescription_record")
public class PrescriptionRecordPo implements Serializable {
    @Serial
    private static final long serialVersionUID = 6505710912240946011L;

    @Id(keyType = KeyType.Generator,value = KeyGenerators.snowFlakeId)
    private String id;
    private String userid;
    private String doctorId;
    private String nurseId;
    private String medicine;
    private LocalDate visitDate;
    private Integer preDays;
    private LocalDate nextVisitDate;
    private LocalDate remindDate;
    private Boolean remindState;
    private Boolean remindEnable;
    private String testPlain;
    private Boolean lastRecord;
    private String remark;
    @Column(onInsertValue = "NOW()")
    private LocalDateTime createTime;
    @Column(onInsertValue = "NOW()", onUpdateValue = "NOW()")
    private LocalDateTime updateTime;


    public static PrescriptionRecordVo toVo(PrescriptionRecordPo po){
        if (Objects.isNull(po))
            return null;

        PrescriptionRecordVo vo = new PrescriptionRecordVo();
        vo.setId(po.getId());
        vo.setUserid(po.getUserid());
        vo.setDoctorId(po.getDoctorId());
        vo.setNurseId(po.getNurseId());
        vo.setMedicine(po.getMedicine());
        vo.setVisitDate(po.getVisitDate());
        vo.setPreDays(po.getPreDays());
        vo.setNextVisitDate(po.getNextVisitDate());
        vo.setRemindDate(po.getRemindDate());
        vo.setRemindState(po.getRemindState());
        vo.setRemindEnable(po.getRemindEnable());
        vo.setTestPlain(po.getTestPlain());
        vo.setLastRecord(po.getLastRecord());
        vo.setRemark(po.getRemark());
        vo.setCreateTime(po.getCreateTime());
        vo.setUpdateTime(po.getUpdateTime());
        return vo;
    }
}