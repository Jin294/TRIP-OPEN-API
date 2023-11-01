import React, { useState, useEffect } from 'react';
import styles from './ExCard.module.css';
import China from '../../assets/img/China.PNG';
import JPN from '../../assets/img/JPN.PNG';
import US from '../../assets/img/US.PNG';
import EU from '../../assets/img/EU.PNG';

import basicHttp from '../../api/basicHttp';
import tokenHttp from '../../api/tokenHttp';

const ExCard = () => {
    useEffect(() => {
        const getExchangeAll = async () => {
            try {
                const res = await basicHttp.get(`/api/exchange`);
                console.log(res.data.data.list);
                console.log(res.data.data.list[2].price);
            } catch (error) {}
        };
        getExchangeAll();
    }, []);

    return (
        <div className={styles.chartContainer}>
            <div className={styles.chartCard}>
                <div className={styles.chartTitle}>
                    <h1>카드 내역 불러오기</h1>
                    {/* <!-- Button --> */}
                </div>
                <div className={styles.link}>
                    <a
                        href=""
                    >
                        내 카드내역 불러오기
                    </a>
                </div>
            </div>
        </div>
    );
};

export default ExCard;
