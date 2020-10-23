package com.water.module.log.service.impl;

import org.springframework.stereotype.Service;

import com.water.base.MyService;
import com.water.module.log.dao.TOperationlogMapper;
import com.water.module.log.entity.TOperationlog;
import com.water.module.log.service.ITOperationlogService;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhangkaiqiang
 * @since 2020-03-27
 */
@Service
public class TOperationlogService extends MyService<TOperationlogMapper, TOperationlog> implements ITOperationlogService {

}
