import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import basicHttp from '../../api/basicHttp';

import styles from './Signup.module.css';

const Signup = () => {

    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [passwordConfirm, setPasswordConfirm] = useState('');

    const handleEmail = (e) => {
        setEmail(e.target.value);
    };
    const handlePassword = (e) => {
        setPassword(e.target.value);
    };
    const handlePasswordConfirm = (e) => {
        setPasswordConfirm(e.target.value);
    };

    const navigate = useNavigate();

    // 회원가입 버튼 클릭
    async function onClickRegister() {
        if (!email) {
            alert('이메일을 입력해주세요');
            return;
        }
        if (!password) {
            alert('비밀번호를 입력해주세요');
            return;
        }
        if (password !== passwordConfirm) {
            alert('비밀번호와 비밀번호 확인이 일치하지 않습니다.');
            return;
        }

        const userData = {
            id: email,
            password: password,
        };

        try {
            const res = await basicHttp.post(`/docs/service/login/signup`, userData);
            console.log(res);
            console.log('회원가입 성공');
            navigate('/login');
            alert('회원가입 성공');
        } catch (error) {
            console.error('회원가입 실패:', error);
            alert('회원가입 실패');
        }
    }

    return (
        <div className={styles.signupBody}>
            <div className={styles.signupContainer}>
                <div className={styles.logoText}>S.F.O.API</div>
                <div className={styles.signupBox}>
                    <label htmlFor="email" className={styles.signupText}>
                        아이디(이메일)
                    </label>
                    <input
                        type="email"
                        name="email"
                        value={email}
                        onChange={handleEmail}
                        className={styles.signupInput}
                        placeholder="아이디(이메일)를 입력해주세요"
                    />
                </div>
                <div className={styles.signupBox}>
                    <label htmlFor="password" className={styles.signupText}>
                        비밀번호
                    </label>
                    <input
                        type="password"
                        name="password"
                        value={password}
                        onChange={handlePassword}
                        className={styles.signupInput}
                        placeholder="비밀번호를 입력해주세요"
                    />
                </div>
                <div className={styles.signupBox}>
                    <label htmlFor="passwordConfirm" className={styles.signupText}>
                        비밀번호 확인
                    </label>
                    <input
                        type="password"
                        name="passwordConfirm"
                        value={passwordConfirm}
                        onChange={handlePasswordConfirm}
                        className={styles.signupInput}
                        placeholder="비밀번호를 입력해주세요"
                    />
                </div>
                <button className={styles.signupBtn} onClick={onClickRegister}>
                    가입하기
                </button>
            </div>
        </div>
    );
};

export default Signup;
