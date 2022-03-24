package com.example.service.VO;

/**
 * @author 唐孝顺
 * @date 2022/3/23 18:10
 */
public class OrderInfo {
    private String member;
    private String setmeal;
    private String orderDate;
    private String orderType;

    public OrderInfo() {
    }

    public OrderInfo(String member, String setmeal, String orderDate, String orderType) {
        this.member = member;
        this.setmeal = setmeal;
        this.orderDate = orderDate;
        this.orderType = orderType;
    }

    public String getMember() {
        return member;
    }

    public void setMember(String memberName) {
        this.member = memberName;
    }

    public String getSetmeal() {
        return setmeal;
    }

    public void setSetmeal(String setmealName) {
        this.setmeal = setmealName;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }
}
