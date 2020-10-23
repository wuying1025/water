package com.water.module.log.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author zhangkaiqiang
 * @since 2020-03-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("T_OPERATIONLOG")
@ApiModel(value="TOperationlog对象", description="")
public class TOperationlog implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.UUID)
    @TableField("ID")
    private String id;
    @ApiModelProperty(value = "功能名称")
    @TableField("FUNCTIONNAME")
    private String functionname;

    @ApiModelProperty(value = "操作类型")
    @TableField("OPERATIONTYPE")
    private String operationtype;

    @ApiModelProperty(value = "操作内容")
    @TableField("OPERATIONCONTENT")
    private String operationcontent;

    @ApiModelProperty(value = "创建者")
    @TableField("CREATOR")
    private String creator;

    @ApiModelProperty(value = "创建时间")
    @TableField("CREATEDDATE")
    private LocalDateTime createddate;


}
