package com.yicj.study.entity;

    import java.time.LocalDateTime;
    import java.io.Serializable;
    import lombok.Data;
    import lombok.EqualsAndHashCode;
    import lombok.experimental.Accessors;

/**
* <p>
    * 
    * </p>
*
* @author yicj
* @since 2019-09-01
*/
    @Data
        @EqualsAndHashCode(callSuper = false)
    @Accessors(chain = true)
    public class User implements Serializable {

    private static final long serialVersionUID = 1L;

            /**
            * 主键
            */
    private Long id;

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
    private LocalDateTime createTime;

    private Integer version;

    private String tenantId;

            /**
            * 逻辑删除
            */
    private Integer deleted;

            /**
            * 最后修改日期
            */
    private LocalDateTime updateTime;


}
