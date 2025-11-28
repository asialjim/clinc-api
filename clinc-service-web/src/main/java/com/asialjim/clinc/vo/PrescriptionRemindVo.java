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

package com.asialjim.clinc.vo;

import com.asialjim.microapplet.sensitive.annotation.Sensitive;
import com.asialjim.microapplet.sensitive.handler.SensitiveType;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * 提示信息
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/10/22, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Data
@Accessors(chain = true)
public class PrescriptionRemindVo implements Serializable {
    @Serial
    private static final long serialVersionUID = 3340006264232664656L;

    /**
     * 记录编号
     */
    private String id;

    /**
     * 上次日期
     */
    private LocalDate lastDate;

    /**
     * 上次有效期
     */
    private Integer lastDays;

    /**
     * 下次日期
     */
    private LocalDate nextDate;

    /**
     * 用户手机号
     */
    @Sensitive(SensitiveType.ChineseMobilePhone)
    private String phone;
}