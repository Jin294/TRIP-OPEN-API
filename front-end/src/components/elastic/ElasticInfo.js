// src/components/ElasticInfoInfo.js
import React, { useEffect, useState } from "react";
import styles from "./ElasticInfo.module.css";

import basicHttp from "../../api/basicHttp";
import { NavLink } from "react-router-dom";

const ElasticInfo = () => {
  return (
    <div className={styles.backBody}>
      <div className={styles.boardContainer}></div>
    </div>
  );
};

export default ElasticInfo;
