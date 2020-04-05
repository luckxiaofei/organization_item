create table litemall_ad
(
    id          int auto_increment
        primary key,
    name        varchar(63)  default '' not null comment '广告标题',
    link        varchar(255) default '' not null comment '所广告的商品页面或者活动页面链接地址',
    url         varchar(255)            not null comment '广告宣传图片',
    position    tinyint(3)   default 1  null comment '广告位置：1则是首页',
    content     varchar(255) default '' null comment '活动内容',
    start_time  datetime                null comment '广告开始时间',
    end_time    datetime                null comment '广告结束时间',
    enabled     tinyint(1)   default 0  null comment '是否启动',
    add_time    datetime                null comment '创建时间',
    update_time datetime                null comment '更新时间',
    deleted     tinyint(1)   default 0  null comment '逻辑删除'
)
    comment '广告表' charset = utf8mb4;

create index enabled
    on litemall_ad (enabled);

create table litemall_address
(
    id             int auto_increment
        primary key,
    name           varchar(63)  default '' not null comment '收货人名称',
    user_id        int          default 0  not null comment '用户表的用户ID',
    province       varchar(63)             not null comment '行政区域表的省ID',
    city           varchar(63)             not null comment '行政区域表的市ID',
    county         varchar(63)             not null comment '行政区域表的区县ID',
    address_detail varchar(127) default '' not null comment '详细收货地址',
    area_code      char(6)                 null comment '地区编码',
    postal_code    char(6)                 null comment '邮政编码',
    tel            varchar(20)  default '' not null comment '手机号码',
    is_default     tinyint(1)   default 0  not null comment '是否默认地址',
    add_time       datetime                null comment '创建时间',
    update_time    datetime                null comment '更新时间',
    deleted        tinyint(1)   default 0  null comment '逻辑删除'
)
    comment '收货地址表' charset = utf8mb4;

create index user_id
    on litemall_address (user_id);

create table litemall_admin
(
    id              int auto_increment
        primary key,
    username        varchar(63)  default ''   not null comment '管理员名称',
    password        varchar(63)  default ''   not null comment '管理员密码',
    last_login_ip   varchar(63)  default ''   null comment '最近一次登录IP地址',
    last_login_time datetime                  null comment '最近一次登录时间',
    avatar          varchar(255) default '''' null comment '头像图片',
    add_time        datetime                  null comment '创建时间',
    update_time     datetime                  null comment '更新时间',
    deleted         tinyint(1)   default 0    null comment '逻辑删除',
    role_ids        varchar(127) default '[]' null comment '角色列表'
)
    comment '管理员表' charset = utf8mb4;

create table litemall_aftersale
(
    id           int auto_increment
        primary key,
    aftersale_sn varchar(63)                 null comment '售后编号',
    order_id     int                         not null comment '订单ID',
    user_id      int                         not null comment '用户ID',
    type         smallint       default 0    null comment '售后类型，0是未收货退款，1是已收货（无需退货）退款，2用户退货退款',
    reason       varchar(31)    default ''   null comment '退款原因',
    amount       decimal(10, 2) default 0.00 null comment '退款金额',
    pictures     varchar(1023)  default '[]' null comment '退款凭证图片链接数组',
    comment      varchar(511)   default ''   null comment '退款说明',
    status       smallint       default 0    null comment '售后状态，0是可申请，1是用户已申请，2是管理员审核通过，3是管理员退款成功，4是管理员审核拒绝，5是用户已取消',
    handle_time  datetime                    null comment '管理员操作时间',
    add_time     datetime                    null comment '添加时间',
    update_time  datetime                    null comment '更新时间',
    deleted      tinyint(1)     default 0    null comment '售后编号'
)
    comment '售后表' charset = utf8mb4;

create table litemall_brand
(
    id          int auto_increment
        primary key,
    name        varchar(255)   default ''   not null comment '品牌商名称',
    `desc`      varchar(255)   default ''   not null comment '品牌商简介',
    pic_url     varchar(255)   default ''   not null comment '品牌商页的品牌商图片',
    sort_order  tinyint(3)     default 50   null,
    floor_price decimal(10, 2) default 0.00 null comment '品牌商的商品低价，仅用于页面展示',
    add_time    datetime                    null comment '创建时间',
    update_time datetime                    null comment '更新时间',
    deleted     tinyint(1)     default 0    null comment '逻辑删除'
)
    comment '品牌商表' charset = utf8mb4;

create table litemall_cart
(
    id               int auto_increment
        primary key,
    user_id          int                         null comment '用户表的用户ID',
    goods_id         int                         null comment '商品表的商品ID',
    goods_sn         varchar(63)                 null comment '商品编号',
    goods_name       varchar(127)                null comment '商品名称',
    product_id       int                         null comment '商品货品表的货品ID',
    price            decimal(10, 2) default 0.00 null comment '商品货品的价格',
    number           smallint(5)    default 0    null comment '商品货品的数量',
    specification_id int                         null comment '商品规格id',
    specifications   varchar(100)                null comment '商品规格',
    checked          tinyint(1)     default 1    null comment '购物车中商品是否选择状态',
    pic_url          varchar(255)                null comment '商品图片或者商品货品图片',
    add_time         datetime                    null comment '创建时间',
    update_time      datetime                    null comment '更新时间',
    deleted          tinyint(1)     default 0    null comment '逻辑删除'
)
    comment '购物车商品表' charset = utf8mb4;

create table litemall_category
(
    id          int auto_increment
        primary key,
    name        varchar(63)   default ''   not null comment '类目名称',
    keywords    varchar(1023) default ''   not null comment '类目关键字，以JSON数组格式',
    `desc`      varchar(255)  default ''   null comment '类目广告语介绍',
    pid         int           default 0    not null comment '父类目ID',
    icon_url    varchar(255)  default ''   null comment '类目图标',
    pic_url     varchar(255)  default ''   null comment '类目图片',
    level       varchar(255)  default 'L1' null,
    sort_order  tinyint(3)    default 50   null comment '排序',
    add_time    datetime                   null comment '创建时间',
    update_time datetime                   null comment '更新时间',
    deleted     tinyint(1)    default 0    null comment '逻辑删除'
)
    comment '类目表' charset = utf8mb4;

create index parent_id
    on litemall_category (pid);

create table litemall_collect
(
    id          int auto_increment
        primary key,
    user_id     int        default 0 not null comment '用户表的用户ID',
    value_id    int        default 0 not null comment '如果type=0，则是商品ID；如果type=1，则是专题ID',
    type        tinyint(3) default 0 not null comment '收藏类型，如果type=0，则是商品ID；如果type=1，则是专题ID',
    add_time    datetime             null comment '创建时间',
    update_time datetime             null comment '更新时间',
    deleted     tinyint(1) default 0 null comment '逻辑删除'
)
    comment '收藏表' charset = utf8mb4;

create index goods_id
    on litemall_collect (value_id);

create index user_id
    on litemall_collect (user_id);

create table litemall_comment
(
    id            int auto_increment
        primary key,
    value_id      int        default 0 not null comment '如果type=0，则是商品评论；如果是type=1，则是专题评论。',
    type          tinyint(3) default 0 not null comment '评论类型，如果type=0，则是商品评论；如果是type=1，则是专题评论；',
    content       varchar(1023)        not null comment '评论内容',
    admin_content varchar(511)         not null comment '管理员回复内容',
    user_id       int        default 0 not null comment '用户表的用户ID',
    has_picture   tinyint(1) default 0 null comment '是否含有图片',
    pic_urls      varchar(1023)        null comment '图片地址列表，采用JSON数组格式',
    star          smallint   default 1 null comment '评分， 1-5',
    add_time      datetime             null comment '创建时间',
    update_time   datetime             null comment '更新时间',
    deleted       tinyint(1) default 0 null comment '逻辑删除'
)
    comment '评论表' charset = utf8mb4;

create index id_value
    on litemall_comment (value_id);

create table litemall_coupon
(
    id          int auto_increment
        primary key,
    name        varchar(63)                 not null comment '优惠券名称',
    `desc`      varchar(127)   default ''   null comment '优惠券介绍，通常是显示优惠券使用限制文字',
    tag         varchar(63)    default ''   null comment '优惠券标签，例如新人专用',
    total       int            default 0    not null comment '优惠券数量，如果是0，则是无限量',
    discount    decimal(10, 2) default 0.00 null comment '优惠金额，',
    min         decimal(10, 2) default 0.00 null comment '最少消费金额才能使用优惠券。',
    `limit`     smallint       default 1    null comment '用户领券限制数量，如果是0，则是不限制；默认是1，限领一张.',
    type        smallint       default 0    null comment '优惠券赠送类型，如果是0则通用券，用户领取；如果是1，则是注册赠券；如果是2，则是优惠券码兑换；',
    status      smallint       default 0    null comment '优惠券状态，如果是0则是正常可用；如果是1则是过期; 如果是2则是下架。',
    goods_type  smallint       default 0    null comment '商品限制类型，如果0则全商品，如果是1则是类目限制，如果是2则是商品限制。',
    goods_value varchar(1023)  default '[]' null comment '商品限制值，goods_type如果是0则空集合，如果是1则是类目集合，如果是2则是商品集合。',
    code        varchar(63)                 null comment '优惠券兑换码',
    time_type   smallint       default 0    null comment '有效时间限制，如果是0，则基于领取时间的有效天数days；如果是1，则start_time和end_time是优惠券有效期；',
    days        smallint       default 0    null comment '基于领取时间的有效天数days。',
    start_time  datetime                    null comment '使用券开始时间',
    end_time    datetime                    null comment '使用券截至时间',
    add_time    datetime                    null comment '创建时间',
    update_time datetime                    null comment '更新时间',
    deleted     tinyint(1)     default 0    null comment '逻辑删除'
)
    comment '优惠券信息及规则表' charset = utf8mb4;

create index code
    on litemall_coupon (code);

create table litemall_coupon_user
(
    id          int auto_increment
        primary key,
    user_id     int                  not null comment '用户ID',
    coupon_id   int                  not null comment '优惠券ID',
    status      smallint   default 0 null comment '使用状态, 如果是0则未使用；如果是1则已使用；如果是2则已过期；如果是3则已经下架；',
    used_time   datetime             null comment '使用时间',
    start_time  datetime             null comment '有效期开始时间',
    end_time    datetime             null comment '有效期截至时间',
    order_id    int                  null comment '订单ID',
    add_time    datetime             null comment '创建时间',
    update_time datetime             null comment '更新时间',
    deleted     tinyint(1) default 0 null comment '逻辑删除'
)
    comment '优惠券用户使用表' charset = utf8mb4;

create table litemall_feedback
(
    id          int auto_increment
        primary key,
    user_id     int         default 0  not null comment '用户表的用户ID',
    username    varchar(63) default '' not null comment '用户名称',
    mobile      varchar(20) default '' not null comment '手机号',
    feed_type   varchar(63) default '' not null comment '反馈类型',
    content     varchar(1023)          not null comment '反馈内容',
    status      int(3)      default 0  not null comment '状态',
    has_picture tinyint(1)  default 0  null comment '是否含有图片',
    pic_urls    varchar(1023)          null comment '图片地址列表，采用JSON数组格式',
    add_time    datetime               null comment '创建时间',
    update_time datetime               null comment '更新时间',
    deleted     tinyint(1)  default 0  null comment '逻辑删除'
)
    comment '意见反馈表' charset = utf8mb4;

create index id_value
    on litemall_feedback (status);

create table litemall_footprint
(
    id          int auto_increment
        primary key,
    user_id     int        default 0 not null comment '用户表的用户ID',
    goods_id    int        default 0 not null comment '浏览商品ID',
    add_time    datetime             null comment '创建时间',
    update_time datetime             null comment '更新时间',
    deleted     tinyint(1) default 0 null comment '逻辑删除'
)
    comment '用户浏览足迹表' charset = utf8mb4;

create table litemall_goods
(
    id            int auto_increment
        primary key,
    goods_sn      varchar(63)    default ''        not null comment '商品编号',
    name          varchar(127)   default ''        not null comment '商品名称',
    category_id   int            default 0         null comment '商品所属类目ID',
    brand_id      int            default 0         null,
    gallery       varchar(1023)                    null comment '商品宣传图片列表，采用JSON数组格式',
    keywords      varchar(255)   default ''        null comment '商品关键字，采用逗号间隔',
    brief         varchar(255)   default ''        null comment '商品简介',
    pic_url       varchar(255)                     null comment '商品页面商品图片',
    share_url     varchar(255)                     null comment '商品分享朋友圈图片',
    is_on_sale    tinyint(1)     default 1         null comment '是否上架',
    is_new        tinyint(1)     default 0         null comment '是否新品首发，如果设置则可以在新品首发页面展示',
    is_hot        tinyint(1)     default 0         null comment '是否人气推荐，如果设置则可以在人气推荐页面展示',
    is_convert    tinyint(1)     default 0         null comment '是否兑换',
    commission    decimal(10, 2)                   null comment '给上级的佣金',
    convert_score int                              null comment '兑换积分',
    give_score    int                              null comment '赠送积分',
    counter_price decimal(10, 2) default 0.00      null comment '专柜价格',
    retail_price  decimal(10, 2) default 100000.00 null comment '零售价格',
    unit          varchar(31)    default '’件‘'     null comment '商品单位，例如件、盒',
    detail        text                             null comment '商品详细介绍，是富文本格式',
    sort_order    smallint(4)    default 100       null,
    add_time      datetime                         null comment '创建时间',
    update_time   datetime                         null comment '更新时间',
    deleted       tinyint(1)     default 0         null comment '逻辑删除'
)
    comment '商品基本信息表' charset = utf8mb4;

create index brand_id
    on litemall_goods (brand_id);

create index cat_id
    on litemall_goods (category_id);

create index goods_sn
    on litemall_goods (goods_sn);

create index sort_order
    on litemall_goods (sort_order);

create table litemall_goods_attribute
(
    id          int auto_increment
        primary key,
    goods_id    int        default 0 not null comment '商品表的商品ID',
    attribute   varchar(255)         not null comment '商品参数名称',
    value       varchar(255)         not null comment '商品参数值',
    add_time    datetime             null comment '创建时间',
    update_time datetime             null comment '更新时间',
    deleted     tinyint(1) default 0 null comment '逻辑删除'
)
    comment '商品参数表' charset = utf8mb4;

create index goods_id
    on litemall_goods_attribute (goods_id);

create table litemall_goods_product
(
    id              int auto_increment
        primary key,
    goods_id        int        default 0 not null comment '商品表的商品ID',
    specification   varchar(50)          not null comment '商品规格',
    specificationId int                  null comment '商品规格Id',
    number          int        default 0 not null comment '商品货品数量',
    url             varchar(125)         null comment '商品货品图片',
    add_time        datetime             null comment '创建时间',
    update_time     datetime             null comment '更新时间',
    deleted         tinyint(1) default 0 null comment '逻辑删除'
)
    comment '商品货品表' charset = utf8mb4;

create index goods_id
    on litemall_goods_product (goods_id);

create table litemall_goods_specification
(
    id            int auto_increment
        primary key,
    goods_id      int          default 0  not null comment '商品表的商品ID',
    specification varchar(255) default '' not null comment '商品规格名称',
    pic_url       varchar(255) default '' not null comment '商品规格图片',
    add_time      datetime                null comment '创建时间',
    update_time   datetime                null comment '更新时间',
    deleted       tinyint(1)   default 0  null comment '逻辑删除'
)
    comment '商品规格表' charset = utf8mb4;

create index goods_id
    on litemall_goods_specification (goods_id);

create table litemall_groupon
(
    id                int auto_increment
        primary key,
    order_id          int                  not null comment '关联的订单ID',
    groupon_id        int        default 0 null comment '如果是开团用户，则groupon_id是0；如果是参团用户，则groupon_id是团购活动ID',
    rules_id          int                  not null comment '团购规则ID，关联litemall_groupon_rules表ID字段',
    user_id           int                  not null comment '用户ID',
    share_url         varchar(255)         null comment '团购分享图片地址',
    creator_user_id   int                  not null comment '开团用户ID',
    creator_user_time datetime             null comment '开团时间',
    status            smallint   default 0 null comment '团购活动状态，开团未支付则0，开团中则1，开团失败则2',
    add_time          datetime             not null comment '创建时间',
    update_time       datetime             null comment '更新时间',
    deleted           tinyint(1) default 0 null comment '逻辑删除'
)
    comment '团购活动表' charset = utf8mb4;

create table litemall_groupon_rules
(
    id              int auto_increment
        primary key,
    goods_id        int                  not null comment '商品表的商品ID',
    goods_name      varchar(127)         not null comment '商品名称',
    pic_url         varchar(255)         null comment '商品图片或者商品货品图片',
    discount        decimal(63)          not null comment '优惠金额',
    discount_member int                  not null comment '达到优惠条件的人数',
    expire_time     datetime             null comment '团购过期时间',
    status          smallint   default 0 null comment '团购规则状态，正常上线则0，到期自动下线则1，管理手动下线则2',
    add_time        datetime             not null comment '创建时间',
    update_time     datetime             null comment '更新时间',
    deleted         tinyint(1) default 0 null comment '逻辑删除'
)
    comment '团购规则表' charset = utf8mb4;

create index goods_id
    on litemall_groupon_rules (goods_id);

create table litemall_issue
(
    id          int auto_increment
        primary key,
    question    varchar(255)         null comment '问题标题',
    answer      varchar(255)         null comment '问题答案',
    add_time    datetime             null comment '创建时间',
    update_time datetime             null comment '更新时间',
    deleted     tinyint(1) default 0 null comment '逻辑删除'
)
    comment '常见问题表' charset = utf8mb4;

create table litemall_keyword
(
    id          int auto_increment
        primary key,
    keyword     varchar(127) default ''  not null comment '关键字',
    url         varchar(255) default ''  not null comment '关键字的跳转链接',
    is_hot      tinyint(1)   default 0   not null comment '是否是热门关键字',
    is_default  tinyint(1)   default 0   not null comment '是否是默认关键字',
    sort_order  int          default 100 not null comment '排序',
    add_time    datetime                 null comment '创建时间',
    update_time datetime                 null comment '更新时间',
    deleted     tinyint(1)   default 0   null comment '逻辑删除'
)
    comment '关键字表' charset = utf8mb4;

create table litemall_log
(
    id          int auto_increment
        primary key,
    admin       varchar(45)          null comment '管理员',
    ip          varchar(45)          null comment '管理员地址',
    type        int                  null comment '操作分类',
    action      varchar(45)          null comment '操作动作',
    status      tinyint(1)           null comment '操作状态',
    result      varchar(127)         null comment '操作结果，或者成功消息，或者失败消息',
    comment     varchar(255)         null comment '补充信息',
    add_time    datetime             null comment '创建时间',
    update_time datetime             null comment '更新时间',
    deleted     tinyint(1) default 0 null comment '逻辑删除'
)
    comment '操作日志表';

create table litemall_notice
(
    id          int auto_increment
        primary key,
    title       varchar(63)          null comment '通知标题',
    content     varchar(511)         null comment '通知内容',
    admin_id    int        default 0 null comment '创建通知的管理员ID，如果是系统内置通知则是0.',
    add_time    datetime             null comment '创建时间',
    update_time datetime             null comment '更新时间',
    deleted     tinyint(1) default 0 null comment '逻辑删除'
)
    comment '通知表' charset = utf8mb4;

create table litemall_notice_admin
(
    id           int auto_increment
        primary key,
    notice_id    int                  null comment '通知ID',
    notice_title varchar(63)          null comment '通知标题',
    admin_id     int                  null comment '接收通知的管理员ID',
    read_time    datetime             null comment '阅读时间，如果是NULL则是未读状态',
    add_time     datetime             null comment '创建时间',
    update_time  datetime             null comment '更新时间',
    deleted      tinyint(1) default 0 null comment '逻辑删除'
)
    comment '通知管理员表' charset = utf8mb4;

create table litemall_order
(
    id               int auto_increment
        primary key,
    user_id          int                     not null comment '用户表的用户ID',
    order_sn         varchar(63)             not null comment '订单编号',
    order_status     smallint                not null comment '订单状态',
    aftersale_status smallint     default 0  null comment '售后状态，0是可申请，1是用户已申请，2是管理员审核通过，3是管理员退款成功，4是管理员审核拒绝，5是用户已取消',
    consignee        varchar(63)             not null comment '收货人名称',
    mobile           varchar(63)             not null comment '收货人手机号',
    address          varchar(127)            not null comment '收货具体地址',
    message          varchar(512) default '' not null comment '用户订单留言',
    goods_price      decimal(10, 2)          not null comment '商品总费用',
    freight_price    decimal(10, 2)          not null comment '配送费用',
    coupon_price     decimal(10, 2)          null comment '优惠券减免',
    integral_price   decimal(10, 2)          null comment '用户积分减免',
    groupon_price    decimal(10, 2)          null comment '团购优惠价减免',
    order_price      decimal(10, 2)          not null comment '订单费用， = goods_price + freight_price - coupon_price',
    actual_price     decimal(10, 2)          not null comment '实付费用， = order_price - integral_price',
    pay_id           varchar(63)             null comment '微信付款编号',
    pay_time         datetime                null comment '微信付款时间',
    ship_sn          varchar(63)             null comment '发货编号',
    ship_channel     varchar(63)             null comment '发货快递公司',
    ship_time        datetime                null comment '发货开始时间',
    refund_amount    decimal(10, 2)          null comment '实际退款金额，（有可能退款金额小于实际支付金额）',
    refund_type      varchar(63)             null comment '退款方式',
    refund_content   varchar(127)            null comment '退款备注',
    refund_time      datetime                null comment '退款时间',
    confirm_time     datetime                null comment '用户确认收货时间',
    comments         smallint     default 0  null comment '待评价订单商品数量',
    end_time         datetime                null comment '订单关闭时间',
    add_time         datetime                null comment '创建时间',
    update_time      datetime                null comment '更新时间',
    deleted          tinyint(1)   default 0  null comment '逻辑删除',
    order_score      int                     null comment '兑换消耗的积分',
    give_score       int                     null comment '订单赠送积分',
    commission       decimal(10, 2)          null comment '订单给上级的佣金'
)
    comment '订单表' charset = utf8mb4;

create table litemall_order_goods
(
    id             int auto_increment
        primary key,
    order_id       int            default 0    not null comment '订单表的订单ID',
    goods_id       int            default 0    not null comment '商品表的商品ID',
    goods_name     varchar(127)   default ''   not null comment '商品名称',
    goods_sn       varchar(63)    default ''   not null comment '商品编号',
    product_id     int            default 0    not null comment '商品货品表的货品ID',
    number         smallint(5)    default 0    not null comment '商品货品的购买数量',
    price          decimal(10, 2) default 0.00 not null comment '商品货品的售价',
    specifications varchar(1023)               not null comment '商品货品的规格列表',
    pic_url        varchar(255)   default ''   not null comment '商品货品图片或者商品图片',
    comment        int            default 0    null comment '订单商品评论，如果是-1，则超期不能评价；如果是0，则可以评价；如果其他值，则是comment表里面的评论ID。',
    add_time       datetime                    null comment '创建时间',
    update_time    datetime                    null comment '更新时间',
    deleted        tinyint(1)     default 0    null comment '逻辑删除'
)
    comment '订单商品表' charset = utf8mb4;

create index goods_id
    on litemall_order_goods (goods_id);

create index order_id
    on litemall_order_goods (order_id);

create table litemall_permission
(
    id          int auto_increment
        primary key,
    role_id     int                  null comment '角色ID',
    permission  varchar(63)          null comment '权限',
    add_time    datetime             null comment '创建时间',
    update_time datetime             null comment '更新时间',
    deleted     tinyint(1) default 0 null comment '逻辑删除'
)
    comment '权限表' charset = utf8mb4;

create table litemall_region
(
    id   int auto_increment
        primary key,
    pid  int          default 0  not null comment '行政区域父ID，例如区县的pid指向市，市的pid指向省，省的pid则是0',
    name varchar(120) default '' not null comment '行政区域名称',
    type tinyint(3)   default 0  not null comment '行政区域类型，如如1则是省， 如果是2则是市，如果是3则是区县',
    code int          default 0  not null comment '行政区域编码'
)
    comment '行政区域表' charset = utf8mb4;

create index agency_id
    on litemall_region (code);

create index parent_id
    on litemall_region (pid);

create index region_type
    on litemall_region (type);

create table litemall_role
(
    id          int auto_increment
        primary key,
    name        varchar(63)          not null comment '角色名称',
    `desc`      varchar(1023)        null comment '角色描述',
    enabled     tinyint(1) default 1 null comment '是否启用',
    add_time    datetime             null comment '创建时间',
    update_time datetime             null comment '更新时间',
    deleted     tinyint(1) default 0 null comment '逻辑删除',
    constraint name_UNIQUE
        unique (name)
)
    comment '角色表' charset = utf8mb4;

create table litemall_score
(
    id          int auto_increment
        primary key,
    userId      int           not null comment '用户id',
    score       int default 0 null comment '积分',
    share_score int           null comment '分红积分',
    add_time    datetime      null comment '创建时间',
    update_time datetime      null comment '更新时间'
)
    comment '用户积分' charset = utf8mb4;

create index topic_id
    on litemall_score (id);

create table litemall_score_history
(
    id           int auto_increment
        primary key,
    userId       int           not null comment '用户id',
    score        int default 0 not null comment '积分',
    channel      int           not null comment '积分获取渠道（1分享群，2分享好友，3浏览商品，4广告）',
    channer_name varchar(50)   null comment '积分渠道',
    add_time     datetime      null comment '创建时间'
)
    comment '用户积分记录表' charset = utf8mb4;

create index topic_id
    on litemall_score_history (id);

create table litemall_score_rule
(
    id       int auto_increment
        primary key,
    score    int default 0 not null comment '积分',
    type     int           not null comment '类型（1分享群，2分享好友，3浏览商品，4广告）',
    add_time datetime      null comment '创建时间'
)
    comment '用户积分规则' charset = utf8mb4;

create index topic_id
    on litemall_score_rule (id);

create table litemall_search_history
(
    id          int auto_increment
        primary key,
    user_id     int                    not null comment '用户表的用户ID',
    keyword     varchar(63)            not null comment '搜索关键字',
    `from`      varchar(63) default '' not null comment '搜索来源，如pc、wx、app',
    add_time    datetime               null comment '创建时间',
    update_time datetime               null comment '更新时间',
    deleted     tinyint(1)  default 0  null comment '逻辑删除'
)
    comment '搜索历史表' charset = utf8mb4;

create table litemall_storage
(
    id          int auto_increment
        primary key,
    `key`       varchar(63)          not null comment '文件的唯一索引',
    name        varchar(255)         not null comment '文件名',
    type        varchar(20)          not null comment '文件类型',
    size        int                  not null comment '文件大小',
    url         varchar(255)         null comment '文件访问链接',
    add_time    datetime             null comment '创建时间',
    update_time datetime             null comment '更新时间',
    deleted     tinyint(1) default 0 null comment '逻辑删除'
)
    comment '文件存储表' charset = utf8mb4;

create index `key`
    on litemall_storage (`key`);

create table litemall_system
(
    id          int auto_increment
        primary key,
    key_name    varchar(255)         not null comment '系统配置名',
    key_value   varchar(255)         not null comment '系统配置值',
    add_time    datetime             null comment '创建时间',
    update_time datetime             null comment '更新时间',
    deleted     tinyint(1) default 0 null comment '逻辑删除'
)
    comment '系统配置表' charset = utf8mb4;

create table litemall_topic
(
    id          int auto_increment
        primary key,
    title       varchar(255)   default '''' not null comment '专题标题',
    subtitle    varchar(255)   default '''' null comment '专题子标题',
    content     text                        null comment '专题内容，富文本格式',
    price       decimal(10, 2) default 0.00 null comment '专题相关商品最低价',
    read_count  varchar(255)   default '1k' null comment '专题阅读量',
    pic_url     varchar(255)   default ''   null comment '专题图片',
    sort_order  int            default 100  null comment '排序',
    goods       varchar(1023)  default ''   null comment '专题相关商品，采用JSON数组格式',
    add_time    datetime                    null comment '创建时间',
    update_time datetime                    null comment '更新时间',
    deleted     tinyint(1)     default 0    null comment '逻辑删除'
)
    comment '专题表' charset = utf8mb4;

create index topic_id
    on litemall_topic (id);

create table litemall_user
(
    id              int auto_increment
        primary key,
    username        varchar(63)             not null comment '用户名称',
    password        varchar(63)  default '' not null comment '用户密码',
    gender          tinyint(3)   default 0  not null comment '性别：0 未知， 1男， 1 女',
    birthday        date                    null comment '生日',
    last_login_time datetime                null comment '最近一次登录时间',
    last_login_ip   varchar(63)  default '' not null comment '最近一次登录IP地址',
    user_level      tinyint(3)   default 0  null comment '0 普通用户，1 VIP用户，2 高级VIP用户',
    nickname        varchar(63)  default '' not null comment '用户昵称或网络名称',
    mobile          varchar(20)  default '' not null comment '用户手机号码',
    avatar          varchar(255) default '' not null comment '用户头像图片',
    weixin_openid   varchar(63)  default '' not null comment '微信登录openid',
    session_key     varchar(100) default '' not null comment '微信登录会话KEY',
    status          tinyint(3)   default 0  not null comment '0 可用, 1 禁用, 2 注销',
    add_time        datetime                null comment '创建时间',
    update_time     datetime                null comment '更新时间',
    deleted         tinyint(1)   default 0  null comment '逻辑删除',
    constraint user_name
        unique (username)
)
    comment '用户表' charset = utf8mb4;

create table litemall_user_friend
(
    id               int auto_increment
        primary key,
    userId           int                         null comment '用户id',
    friend_user_id   int                         null comment '好友用户id',
    friend_user_name varchar(50)                 null comment '好友用户名',
    commission       decimal(10, 2) default 0.00 null comment '佣金',
    score_commission int            default 0    null comment '积分佣金',
    add_time         datetime                    null comment '创建时间',
    update_time      datetime                    null comment '更新时间'
)
    comment '用户好友表' charset = utf8mb4;

create index topic_id
    on litemall_user_friend (id);

create table litemall_user_money
(
    id          int auto_increment
        primary key,
    userId      int                         not null comment '用户id',
    money       decimal(10, 2) default 0.00 null comment '金额',
    add_time    datetime                    null comment '创建时间',
    update_time datetime                    null comment '更新时间'
)
    comment '用户钱包金额' charset = utf8mb4;

create index topic_id
    on litemall_user_money (id);

create table litemall_user_money_history
(
    id           int auto_increment
        primary key,
    userId       int                         null comment '用户id',
    money        decimal(10, 2) default 0.00 null comment '金额',
    type         int                         not null comment '获取渠道（1下级佣金，2分红，3充值）',
    typeName     varchar(10)                 not null comment '获取渠道（1下级佣金，2分红，3充值）',
    order_number varchar(100)                not null comment '订单号（充值）',
    add_time     datetime                    null comment '创建时间',
    update_time  datetime                    null comment '更新时间'
)
    comment '用户钱包金额' charset = utf8mb4;

create index topic_id
    on litemall_user_money_history (id);

