// pages/myCaeate/myCaeate.js
const app = getApp()
var util = require('../../utils/util.js');
var api = require('../../config/api.js');
var user = require('../../utils/user.js');
import Dialog from '../../miniprogram_npm/@vant/weapp/dialog/dialog';

Page({

    /**
     * 页面的初始数据
     */
    data: {
        subjectList: []
    },

    /**
     * 生命周期函数--监听页面加载
     */
    onLoad: function (options) {
        this.getSubjectList()
    },
    getSubjectList() {
        let that = this;
        util.request(api.getMyCreateSubject, {}).then(function (res) {
            that.setData({
                subjectList: res.data
            })
        });
    },
    deleteSubject(event) {
        let that = this;
        Dialog.confirm({
            title: '删除该主题',
            message: '删除后无法恢复,确认删除?',
            asyncClose: true
        }).then(() => {
            util.request(api.deleteSubject, {
                subjectId: event.currentTarget.dataset.id
            }).then(function (res) {
                Dialog.close();
                that.getSubjectList()
            });
        }).catch(() => {
            Dialog.close();
        });
    },
    goCreateGroup(event) {
        if (event.currentTarget.dataset.type == 0) {
            util.navigateTo("/pages/group/group")
        } else {
            util.navigateTo("/pages/sort/sort")
        }

    }
})
