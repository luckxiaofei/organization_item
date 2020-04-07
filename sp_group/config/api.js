// 以下是业务服务器API地址
// 本机开发时使用
var WxApiRoot = 'https://localhost:8080/wx/';
// 局域网测试使用
// var WxApiRoot = 'http://47.115.124.84:8080/wx/';
// 云平台上线时使用
// var WxApiRoot = 'https://litemall.menethil.com.cn/wx/';
// var WxApiRoot = 'https://www.qdbb.ltd:8080/wx/';

module.exports = {
    AuthLoginByWeixin: WxApiRoot + 'auth/login_by_weixin', //微信登录
    addGroupSubject: WxApiRoot + 'group/addSubject',//新建
    getGroupDetail: WxApiRoot + 'group/detail',//详情
    groupAddDetail: WxApiRoot + 'group/add/detail',//参加
    groupExprot: WxApiRoot + 'group/groupExprot',//导出


    addSortSubject: WxApiRoot + 'sort/addSubject',//新建
    getSortDetail: WxApiRoot + 'sort/detail',//详情
    sortAddDetail: WxApiRoot + 'sort/add/detail',//参加
    sortExprot: WxApiRoot + 'sort/sortExprot',//导出

    getMyCreateSubject: WxApiRoot + 'getMyCreateSubject',
    getMyJoinSubject: WxApiRoot + 'getMyJoinSubject',

    getUserInfo: WxApiRoot + '/user/getUserInfo',
    updateUserInfo: WxApiRoot + '/user/updateUserInfo'

};
