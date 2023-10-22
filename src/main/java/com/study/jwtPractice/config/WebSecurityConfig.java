package com.study.jwtPractice.config;

import com.study.jwtPractice.config.filter.CustomAuthenticationFilter;
import com.study.jwtPractice.config.handler.CustomAuthFailureHandler;
import com.study.jwtPractice.config.handler.CustomAuthSuccessHandler;
import com.study.jwtPractice.config.handler.CustomAuthenticationProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Slf4j
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    /**
     * 1. 정적 자원에 대해서 인증된 사용자가 정적 자원의 접근에 대해 '인가'에 대한 설정을 담당하는 메서드
     */
    @Bean
    WebSecurityCustomizer webSecurityCustomizer() {

        return (web) -> web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    /**
     * 2. HTTP에 대해서 '인증'과 '인가'를 담당하는 메서드이며 필터를 통해 인증 방식과 인증 절차에 대해서 등록하며 설정을 담당하는 메서드
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        // 서버에 인증정보를 저장하지 않기에 csrf를 사용하지 않음


        http.csrf().disable()

                // form 기반의 로그인에 대해 비활성하여 커스텀으로 구성한 필터를 사용
                .formLogin().disable()

                // 토클을 활용하는 경우 모든 요청에 대해 '인가'에 대해서 사용
                .authorizeHttpRequests((authz) -> authz.anyRequest().permitAll())

                // Spring Security Custom Filter Load - Form '인증'에 대해서 사용
                .addFilterBefore(customAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)

                // Session 기반의 인증을 사용하지 않고 추후 JWT를 이용하여 인증
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

                // Spring Security JWT Filter Load
//              .addFilterBefore(jwtAuthrizationFilter(), BasicAuthenticationFilter.class)

                return http.build();
    }

    /**
     * 3. authenticate의 인증 메서드를 제공하는 매니저로 'Provider'의 인터페이스를 의미.
     * - 과정
     *      CustomAuthenticationFilter -> AuthenticationManager(Interface) -> CustomAuthenticationProvider(implements)
     */
    @Bean
    public AuthenticationManager authenticationManager() {

        return new ProviderManager(customAuthenticationProvider());
    }

    /**
     * 4. '인증' 제공자로 사용자의 이름과 비밀번호가 요구됨
     */
    @Bean
    public CustomAuthenticationProvider customAuthenticationProvider() {

        return new CustomAuthenticationProvider(bCryptPasswordEncoder());
    }

    /**
     * 5. 비밀번호를 암호화하기 위한 BCrypt 인코딩을 통하여 비밀번호에 대한 암호화를 수행
     */
    public BCryptPasswordEncoder bCryptPasswordEncoder() {

        return new BCryptPasswordEncoder();
    }

    /**
     * 6. 커스텀을 수행한 '인증' 필터로 접근 URL, 데이터 전달방식(form) 등 인증 과정 및 인증 후 처리에 대한 설정을 구성하는 메서드
     */
    @Bean
    public CustomAuthenticationFilter customAuthenticationFilter() {

        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManager());
        customAuthenticationFilter.setFilterProcessesUrl("user/login"); // 접근 URL

        // 인증 성공, 실패 시 핸들러로 처리를 전가
        customAuthenticationFilter.setAuthenticationSuccessHandler(customLoginSuccessHandler());
        customAuthenticationFilter.setAuthenticationFailureHandler(customLoginFailureHandler());
        customAuthenticationFilter.afterPropertiesSet();
        return customAuthenticationFilter;
    }

    /**
     * 7. Spring Security 기반의 사용자의 정보가 맞을 경우 수행이 되며 결과값을 리턴해주는 handler
     */
    @Bean
    public CustomAuthSuccessHandler customLoginSuccessHandler() {
        return new CustomAuthSuccessHandler();
    }

    /**
     * 8. Spring Security 기반의 사용자의 정보가 맞지 않을 경우 수행이 되며 결과값을 리턴해주는 handler
     */
    @Bean
    public CustomAuthFailureHandler customLoginFailureHandler() {
        return new CustomAuthFailureHandler();
    }

    /**
     * 9. JWT 토큰을 통하여 사용자를 인증
     */
//    @Bean
//    public JwtAuthorizationFilter jwtAuthorizationFilter() {
//        return new JwtAuthorizationFilter();
//    }

}
