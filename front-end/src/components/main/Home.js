import React, { useState } from 'react';

import styles from "./Home.module.css";
import Accept from '../../assets/img/accept-green-128.png'

const CardComponent = ({ index }) => {
  const icons = [
    require('../../assets/img/iconSite.png'),
    require('../../assets/img/iconRestaurant.png'),
    require('../../assets/img/iconSearchbar.png')
  ];
  const titleArray = ["숙소 API", "음식점 API", "검색 API"];
  const descArray = ["숙소 API 짧은 설명", "음식점 API 짧은 설명", "검색 API 짧은 설명"];
  const subtitle1Array = ["숙소 정보", "음식점 정보", "검색 정보"];
  const subDesc1Array = ["숙소명, 장소, 특징3", "음식점명, 장소, 특징3", "검색어특징3"];
  const subtitle2Array = ["정렬", "정렬", "정렬"];
  const subDesc2Array = ["거리순, 인기순", "거리순, 인기순", "거리순, 인기순"];

  const [isCardHovered, setIsCardHovered] = useState(false);

  const handleCardHover = () => {
    setIsCardHovered(true);
  };

  const handleCardLeave = () => {
    setIsCardHovered(false);
  };

  return (
    <div className={styles["card"]} onMouseEnter={handleCardHover} onMouseLeave={handleCardLeave}>
      <div className={`${styles['card-upside']} ${isCardHovered ? styles['up-hovered'] : ''}`}>
        <div className={`${styles['card-upside-title']} ${isCardHovered ? styles['text-hovered'] : ''}`}>
          {titleArray[index]}
        </div>
        <img className={styles["card-upside-icon"]} src={icons[index]} alt="" />
        <div className={`${styles['card-upside-desc']} ${isCardHovered ? styles['text-hovered'] : ''}`}>
          {descArray[index]}
        </div>
      </div>
      <div className={`${styles['card-downside']} ${isCardHovered ? styles['down-hovered'] : ''}`}>
        <div className={styles["card-downside-container"]}>
          <div className={styles["card-downside-info"]}>
            <div className={styles["card-downside-left"]}>
              <img alt="" src={Accept} width={"20px"} />
            </div>
            <div className={styles["card-downside-info-right"]}>
              <div className={styles["card-downside-info-title"]}>
                {subtitle1Array[index]}
              </div>
              <div className={styles["card-downside-info-desc"]}>
                {subDesc1Array[index]}
              </div>
            </div>
            
          </div>
          <div className={styles["card-downside-info"]}>
            <div className={styles["card-downside-left"]}>
              <img alt="" src={Accept} width={"20px"} />
            </div>
            <div className={styles["card-downside-info-right"]}>
              <div className={styles["card-downside-info-title"]}>
                {subtitle2Array[index]}
              </div>
              <div className={styles["card-downside-info-desc"]}>
                {subDesc2Array[index]}
              </div>
            </div>
          </div>
        </div>
        
        <button className={`${styles['card-downside-btn']} ${isCardHovered ? styles['button-hovered'] : ''}`}>
          보러가기
        </button>
      </div>
    </div>
  );
};

const Home = () => {

  return (
    <div className="home">
      <div className={styles["home"]}>
        <div className={styles["home-title"]}>여행지 정보 OPEN API</div>
        <div className={styles["home-subtitle"]}>
          여행지와 숙소, 음식점을 소개
        </div>
        <div className={styles["home-search"]}>
          <div className={styles["home-search-bar"]}>
            <input className={styles["home-search-input"]} placeholder="여행지 검색하기"></input>
            <div className={styles["home-search-btn"]}>
              <svg
                xmlns="http://www.w3.org/2000/svg"
                width="25"
                height="25"
                viewBox="0 0 44 44"
                fill="none"
              >
                <path
                  fill-rule="evenodd"
                  clip-rule="evenodd"
                  d="M31.3396 27.6572H29.3342L28.5813 27.0286C30.9636 24.1374 32.4682 20.4912 32.4682 16.3429C32.4681 7.29117 25.1964 0 16.1717 0C7.27054 0 0 7.29117 0 16.3429C0 25.3946 7.27054 32.6857 16.2966 32.6857C20.3083 32.6857 24.0689 31.177 26.9518 28.7889L27.7036 29.4177V31.4287L40.2394 44L44 40.2286L31.3396 27.6572ZM16.2966 27.6572C10.0287 27.6572 5.0144 22.6285 5.0144 16.3429C5.0144 10.0571 10.0287 5.02872 16.2966 5.02872C22.5643 5.02872 27.5787 10.0571 27.5787 16.3429C27.5787 22.6285 22.5643 27.6572 16.2966 27.6572Z"
                  fill="white"
                />
              </svg>
              <span>검색</span>
            </div>
          </div>
        </div>
      </div>
      <div className={styles["home-info"]}>
        <div className={styles["card-list"]}>
          <CardComponent index={0} />
          <CardComponent index={1} />
          <CardComponent index={2} />
        </div>
      </div>
    </div>
  );
};

export default Home;
