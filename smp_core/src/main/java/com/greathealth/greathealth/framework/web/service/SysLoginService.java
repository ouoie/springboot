package com.greathealth.greathealth.framework.web.service;

import com.greathealth.greathealth.common.constant.Constants;
import com.greathealth.greathealth.common.core.domain.entity.SysUser;
import com.greathealth.greathealth.common.core.domain.model.LoginUser;
import com.greathealth.greathealth.common.enums.UserStatus;
import com.greathealth.greathealth.common.exception.user.CaptchaException;
import com.greathealth.greathealth.common.exception.user.CaptchaExpireException;
import com.greathealth.greathealth.common.exception.user.UserPasswordNotMatchException;
import com.greathealth.greathealth.common.utils.DateUtils;
import com.greathealth.greathealth.common.utils.MessageUtils;
import com.greathealth.greathealth.common.utils.ServletUtils;
import com.greathealth.greathealth.common.utils.StringUtils;
import com.greathealth.greathealth.common.utils.ip.IpUtils;
import com.greathealth.greathealth.system.service.ISysConfigService;
import com.greathealth.greathealth.system.service.ISysUserService;
import com.greathealth.greathealth.common.core.redis.RedisCache;
import com.greathealth.greathealth.common.exception.ServiceException;
import com.greathealth.greathealth.framework.manager.AsyncManager;
import com.greathealth.greathealth.framework.manager.factory.AsyncFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 登录校验方法
 *
 * @author
 */
@Component
public class SysLoginService
{
    @Autowired
    private TokenService tokenService;

    @Resource
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private ISysUserService userService;

    @Autowired
    private ISysConfigService configService;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    /**
     * 登录验证
     *
     * @param username 用户名
     * @param password 密码
     * @param code 验证码
     * @param uuid 唯一标识
     * @return 结果
     */
    public String login(String username, String password, String code, String uuid)
    {
        boolean captchaOnOff = configService.selectCaptchaOnOff();
        // 验证码开关
        if (captchaOnOff)
        {
            validateCaptcha(username, code, uuid);
        }

        // 用户验证
        Authentication authentication = null;
        try
        {
            // 该方法会去调用UserDetailsServiceImpl.loadUserByUsername
            authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(username, password));
        }
        catch (Exception e)
        {
            if (e instanceof BadCredentialsException)
            {
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.password.not.match")));
                throw new UserPasswordNotMatchException();
            }
            else
            {
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, e.getMessage()));
                throw new ServiceException(e.getMessage());
            }
        }
        AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_SUCCESS, MessageUtils.message("user.login.success")));
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();

        if( UserStatus.XFT.getCode().equals(loginUser.getUser().getStatus())){
            throw new ServiceException("对不起，您的账号：" + username + " 需通过薪福通平台登录");
        }
        recordLoginInfo(loginUser.getUserId());
        // 生成token
        return tokenService.createToken(loginUser);
    }

    public String xftLogin(String username)
    {
        UserDetails details = userDetailsService.loadUserByUsername(username);
        AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_SUCCESS, MessageUtils.message("user.login.success")));
        LoginUser loginUser = (LoginUser)details;
        recordLoginInfo(loginUser.getUserId());
         //生成token
        return tokenService.createToken(loginUser);
    }

    /**
     * 校验验证码
     *
     * @param username 用户名
     * @param code 验证码
     * @param uuid 唯一标识
     * @return 结果
     */
    public void validateCaptcha(String username, String code, String uuid)
    {
        String verifyKey = Constants.CAPTCHA_CODE_KEY + StringUtils.nvl(uuid, "");
        String captcha = redisCache.getCacheObject(verifyKey);
        redisCache.deleteObject(verifyKey);
        if (captcha == null)
        {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.jcaptcha.expire")));
            throw new CaptchaExpireException();
        }
        if (!code.equalsIgnoreCase(captcha))
        {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.jcaptcha.error")));
            throw new CaptchaException();
        }
    }

    /**
     * 记录登录信息
     *
     * @param userId 用户ID
     */
    public void recordLoginInfo(String userId)
    {
        SysUser sysUser = new SysUser();
        sysUser.setUserId(userId);
        sysUser.setLoginIp(IpUtils.getIpAddr(ServletUtils.getRequest()));
        sysUser.setLoginDate(DateUtils.getNowDate());
        userService.updateUserProfile(sysUser);
    }
}
