import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useDispatch, useSelector } from 'react-redux';
import basicHttp from '../../api/basicHttp';

import styles from './Signup.module.css';

const Signup = () => {
    const [ssafyArea, setSsafyArea] = useState('');
    const [ssafyClass, setSsafyClass] = useState('');
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [passwordConfirm, setPasswordConfirm] = useState('');
    const handleSsafyClass = (e) => {
        setSsafyClass(e.target.value);
    };
    const handleSsafyArea = (e) => {
        setSsafyArea(e.target.value);
    };
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
    const dispatch = useDispatch();

    // 회원가입 버튼 클릭
    async function onClickRegister() {
        if (!ssafyArea) {
            alert('싸피지역을 입력해주세요');
            return;
        }
        if (!ssafyClass) {
            alert('싸피반을 입력해주세요');
            return;
        }
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
            email: email,
            password: password,
            ssafy_class: ssafyClass,
            area: ssafyArea,
        };

        try {
            const res = await basicHttp.post(`/docs/service`, userData);
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
                {/* <div className={styles.signupTitle}>회원가입</div> */}
                <div className={styles.signupBox2}>
                    <div className={styles.areaBox}>
                        <label htmlFor="nickname" className={styles.signupText2}>
                            싸피 지역
                        </label>
                        <input
                            type="text"
                            name="ssafyArea"
                            value={ssafyArea}
                            onChange={handleSsafyArea}
                            className={styles.signupInput2}
                            placeholder="ex) 대전"
                        />
                    </div>
                    <div className={styles.classBox}>
                        <label htmlFor="ssafyClass" className={styles.signupText2}>
                            싸피 반
                        </label>
                        <input
                            type="text"
                            name="ssafyClass"
                            value={ssafyClass}
                            onChange={handleSsafyClass}
                            className={styles.signupInput2}
                            placeholder="ex) 3"
                        />
                    </div>
                </div>

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
