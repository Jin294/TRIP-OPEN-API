// src/components/ServiceInfo.js
import React, { useEffect, useState } from "react";
import styles from "./ServiceInfo.module.css";

import basicHttp from "../../api/basicHttp";
import { NavLink } from "react-router-dom";

/**
 *
 * 더미 데이터
 */
const dummyData = [
  {
    id: 0,
    title: "여행지 기반 숙소 추천 API",
    detail: "블라블라",
  },
  {
    id: 1,
    title: "사용자의 위도,경도 기반 숙소 추천 API",
    detail: "블라블라",
  },
  {
    id: 2,
    title: "여행지 기반 음식점 추천 API",
    detail: "블라블라",
  },
  {
    id: 3,
    title: "사용자의 위도,경도 기반 음식점 추천 API",
    detail: "블라블라",
  },
  {
    id: 4,
    title: "검색 API",
    detail: "블라블라",
  },
  {
    id: 5,
    title: "검색 API",
    detail: "블라블라",
  },
];

const ServiceInfo = () => {
  const [title, setTitle] = useState([]);
  const [detail, setDetail] = useState([]);
  const [tabUrls, setTabUrls] = useState([]);
  const [exUrls, setExUrls] = useState([]);

  useEffect(() => {
    getAllServiceData();
    getTabUrls();
    getExUrls();
  }, []);

  const getAllServiceData = async () => {
    // try {
    //   const response = await basicHttp.get("/docs/data/apitype");
    //   const responseData = response.data;
    //   console.log("responseData", responseData.data);

    //   if (responseData.data) {
    //     setTitle(responseData.data.map((item) => item.title));
    //     setDetail(responseData.data.map((item) => item.detail));
    //   }
    // } catch (error) {
    //   console.log("Error fetching service data", error);
    // }
    setTitle(dummyData.map((item) => item.title));
    setDetail(dummyData.map((item) => item.detail));
  };

  const getTabUrls = () => {
    const urls = [
      "/apidock/financialdata",
      "/apidock/exchange",
      "/apidock/investment",
      "/apidock/consumption",
    ];
    setTabUrls(urls);
  };
  const getExUrls = () => {
    const urls = ["/excard", "/exexchange", "/excard", "/excard"];
    setExUrls(urls);
  };

  return (
    <div className={styles.backBody}>
      <div className={styles.boardContainer}>
        <div className={styles.boardTop}>
          <h2>제공하는 API를 소개합니다</h2>
        </div>
        <div className={styles.cardList}>
          {title.map((item, index) => (
            <div className={styles.card} key={index}>
              <h3>{item}</h3>
              <div>
                <p>{detail[index]}</p>
              </div>
              {/* <div className={styles.buttonContainer}>
                <NavLink to={tabUrls[index]} className={styles.button}>
                  <p>문서보기</p>
                </NavLink>
                <NavLink to={exUrls[index]} className={styles.button}>
                  <p>사용예시</p>
                </NavLink>
              </div> */}
            </div>
          ))}
        </div>
      </div>
    </div>
  );
};

export default ServiceInfo;
