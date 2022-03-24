package com.example.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.example.dao.MemberDao;
import com.example.pojo.Member;
import com.example.service.MemberService;
import com.example.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 唐孝顺
 * @date 2022/3/24 14:48
 */
@Service(interfaceClass = MemberService.class)
@Transactional
public class MemberServiceImpl implements MemberService {
   @Autowired
   private MemberDao memberDao;
   //新增会员
    @Override
    public void add(Member member) {
        if(member.getPassword() != null){
            //将密码进行加密
            String password = MD5Utils.md5(member.getPassword());
            member.setPassword(password);
        }
        memberDao.add(member);
    }

    @Override
    public Member findByEmail(String email) {
        return memberDao.findByEmail(email);
    }
}
