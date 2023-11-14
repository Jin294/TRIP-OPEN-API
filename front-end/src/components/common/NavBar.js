import React from "react";
import styles from "./NavBar.module.css";

import { Link, useNavigate, useLocation } from "react-router-dom";
import { useDispatch, useSelector } from "react-redux";
import { logout } from "../../redux/userInfo";

import Logo from "../../assets/img/Logo.png";

const NavBar = () => {
  const dispatch = useDispatch();
  const userInfo = useSelector((state) => {
    return state.persistedReducer.userInfo;
  });
  const navigate = useNavigate();
  const location = useLocation();

  // 특정 경로에서는 NavBar를 숨깁니다.
  const hideNavBar =
    location.pathname === "/exexchange" ||
    location.pathname === "/excard" ||
    location.pathname === "/excardcontent";

  const onClickLogout = () => {
    localStorage.removeItem("access-token");
    localStorage.removeItem("refresh-token");
    dispatch(logout());
    navigate("/");
  };

  const onClick = () => {
    navigate("/mypage");
  };
  return (
    <div className="NavBar">
      {!hideNavBar && (
        // NavBar 내용
        <div className={styles.mainNav}>
          <Link className={styles.navMenu} to="/">
            <img className={styles.navLogo} src={Logo} alt="" />
          </Link>
          <div className={styles.navLeft}>
            <Link className={styles.navMenu} to="/">
              Home
            </Link>
            <Link className={styles.navMenu} to="/apidocs/financialdata">
              API문서
            </Link>
          </div>
          {/* 로그인 상태에 따라 Login 또는 Mypage로 링크 변경 */}
          {userInfo === null || Object.keys(userInfo).length === 0 ? (
            <div className={styles.navRight}>
            <Link className={styles.navMenu} to="/login">
              로그인
            </Link>
            <Link className={styles.navMenu} to="/signup">
              회원가입
            </Link>
          </div>
          ) : (
            <div className={styles.navRight}>
              <button className={styles.navMenu} onclick={onClick}>
                마이페이지
              </button>
              <button className={styles.navMenu} onClick={onClickLogout}>
                로그아웃
              </button>
            </div>
          )}
        </div>
      )}
    </div>
  );
};

export default NavBar;
