package com.yicj.study.user.entity;

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
* @since 2019-08-30
*/
    @Data
        @EqualsAndHashCode(callSuper = true)
    @Accessors(chain = true)
    public class User extends BaseEntity {

    private static final long serialVersionUID = 1L;

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


}
