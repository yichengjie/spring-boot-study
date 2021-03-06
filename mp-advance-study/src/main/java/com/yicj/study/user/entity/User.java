package com.yicj.study.user.entity;

    import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.Version;
import com.yicj.study.common.BaseEntity;
    import java.time.LocalDateTime;
    import lombok.Data;
    import lombok.EqualsAndHashCode;
    import lombok.experimental.Accessors;

/**
* <p>
    * 
    * </p>
*
* @author yicj
* @since 2019-08-31
*/
    @Data
    @EqualsAndHashCode(callSuper = true)
    @Accessors(chain = true)
    public class User extends BaseEntity {

    private static final long serialVersionUID = 1L;
    /**
     * 主键
     */
    private Long id ;
            /**
            * 姓名
            */
    private String name;

            /**
            * 年龄
            */
    private Integer age;

            /**
            * 邮箱
            */
    private String email;

            /**
            * 直属上级id
            */
    private Long managerId;

            /**
            * 创建时间
            */
    @TableField(fill=FieldFill.INSERT)
    private LocalDateTime createTime;
    //最后更新日期
    @TableField(fill=FieldFill.UPDATE)
    private LocalDateTime updateTime ;
    
    //版本号
    @Version
    private Integer version ;
    
    //逻辑删除
    @TableLogic
    @TableField(select=false)
    private Integer deleted ;
}
