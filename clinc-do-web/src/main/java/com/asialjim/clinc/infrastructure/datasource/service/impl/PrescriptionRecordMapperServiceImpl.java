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

package com.asialjim.clinc.infrastructure.datasource.service.impl;

import com.asialjim.clinc.infrastructure.cache.ClincCache;
import com.asialjim.clinc.infrastructure.datasource.mapper.PrescriptionRecordBaseMapper;
import com.asialjim.clinc.infrastructure.datasource.po.PrescriptionRecordPo;
import com.asialjim.clinc.infrastructure.datasource.service.PrescriptionRecordMapperService;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

/**
 * 开药记录持久化
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/10/22, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Repository
public class PrescriptionRecordMapperServiceImpl extends ServiceImpl<PrescriptionRecordBaseMapper, PrescriptionRecordPo> implements PrescriptionRecordMapperService {

    @Override
    @Cacheable(value = ClincCache.Name.prescriptionRecordById, key = "#id")
    public PrescriptionRecordPo queryById(String id) {
        return getById(id);
    }
}