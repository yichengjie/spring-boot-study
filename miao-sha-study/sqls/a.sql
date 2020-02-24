CREATE TABLE `user_info` (
   `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
   `name` varchar(64) COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
   `gendeer` tinyint(1) NOT NULL DEFAULT '0' COMMENT '1代表男性，2代表女性',
   `age` int(11) NOT NULL DEFAULT '0',
   `telephone` varchar(20) COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
   `register_mode` varchar(20) COLLATE utf8_unicode_ci NOT NULL DEFAULT '' COMMENT 'byphone,bywechat,byalipay',
   `thrid_part_id` varchar(64) COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
   PRIMARY KEY (`id`)
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci


 CREATE TABLE `user_password` (
   `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
   `encrpt_password` varchar(128) COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
   `user_id` int(11) NOT NULL DEFAULT '0',
   PRIMARY KEY (`id`)
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci