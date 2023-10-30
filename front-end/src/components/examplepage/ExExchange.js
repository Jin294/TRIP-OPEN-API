import React, { useState, useEffect } from 'react';
import styles from './ExExchange.module.css';
import China from '../../assets/img/China.PNG';
import JPN from '../../assets/img/JPN.PNG';
import US from '../../assets/img/US.PNG';
import EU from '../../assets/img/EU.PNG';

import basicHttp from '../../api/basicHttp';
import tokenHttp from '../../api/tokenHttp';

const ExExchange = () => {
    const [exchangeAllList, setExchangeAllList] = useState([]);

    useEffect(() => {
        const getExchangeAll = async () => {
            try {
                const res = await basicHttp.get(`/api/exchange`);
                console.log(res.data.data.list);
                console.log(res.data.data.list[2].price);

                setExchangeAllList(res.data.data.list);
            } catch (error) {}
        };
        getExchangeAll();
    }, []);

    // exchangeAllList 배열의 길이가 3보다 크거나 같을 때만 해당 값을 사용
    const exchangeData = exchangeAllList.length ? exchangeAllList[2] : null;

    return (
        <div className={styles.chartContainer}>
            <div className={styles.chartCard}>
                <div className={styles.chartTitle}>
                    <h1>환율</h1>
                    <p>
                        오늘의 환율 (하나은행 기준)
                        <br />
                        (조회시간 : 2023-10-04 10:38:34)
                    </p>
                </div>

                <div className={`${styles.chartBox} ${styles.chatBox1}`}>
                    <div className={`${styles.chartNumber} ${styles.chartNumber1}`}>
                        <img src={US} alt="03" />
                    </div>
                    <div className={`${styles.chartCover} ${styles.chartCover1}`}></div>
                    {/* <!-- Name --> */}
                    <div className={`${styles.chartName} ${styles.chartName1}`}>
                        <span> 미국</span>
                        {/* exchangeData가 null이 아닐 때만 해당 값을 출력 */}
                        {exchangeData && <div className={styles.price}>{exchangeAllList[2].price}</div>}
                        <div className={styles.priceContainer}>
                            <div className={styles.priceTitle}>현찰 살 때</div>
                            {exchangeData && (
                                <div className={styles.priceContent}>{exchangeAllList[2].cashBuyPrice}</div>
                            )}
                        </div>
                        <div className={styles.separator}></div>
                        <div className={styles.priceContainer}>
                            <div className={styles.priceTitle}>현찰 팔 때</div>
                            {exchangeData && (
                                <div className={styles.priceContent}>{exchangeAllList[2].cashSellPrice}</div>
                            )}
                        </div>
                    </div>
                    {/* <!-- Button --> */}
                    {/* <div className={styles.link}>
                        <a href="https://www.youtube.com/watch?v=wnJ6LuUFpMo">Listen</a>
                    </div> */}
                </div>

                {/* <!-- Separator --> */}
                <div className={styles.separator}></div>

                <div className={`${styles.chartBox} ${styles.chatBox1}`}>
                    <div className={`${styles.chartNumber} ${styles.chartNumber1}`}>
                        <img src={EU} alt="03" />
                    </div>
                    <div className={`${styles.chartCover} ${styles.chartCover1}`}></div>
                    {/* <!-- Name --> */}
                    <div className={`${styles.chartName} ${styles.chartName1}`}>
                        <span> 유럽</span>
                        {/* exchangeData가 null이 아닐 때만 해당 값을 출력 */}
                        {exchangeData && <div className={styles.price}>{exchangeAllList[3].price}</div>}
                        <div className={styles.priceContainer}>
                            <div className={styles.priceTitle}>현찰 살 때</div>
                            {exchangeData && (
                                <div className={styles.priceContent}>{exchangeAllList[3].cashBuyPrice}</div>
                            )}
                        </div>
                        <div className={styles.separator}></div>
                        <div className={styles.priceContainer}>
                            <div className={styles.priceTitle}>현찰 팔 때</div>
                            {exchangeData && (
                                <div className={styles.priceContent}>{exchangeAllList[3].cashSellPrice}</div>
                            )}
                        </div>
                    </div>
                    {/* <!-- Button --> */}
                    {/* <div className={styles.link}>
                        <a href="https://www.youtube.com/watch?v=wnJ6LuUFpMo">Listen</a>
                    </div> */}
                </div>

                {/* <!-- Separator --> */}
                <div className={styles.separator}></div>

                <div className={`${styles.chartBox} ${styles.chatBox1}`}>
                    <div className={`${styles.chartNumber} ${styles.chartNumber1}`}>
                        <img src={China} alt="03" />
                    </div>
                    <div className={`${styles.chartCover} ${styles.chartCover1}`}></div>
                    {/* <!-- Name --> */}
                    <div className={`${styles.chartName} ${styles.chartName1}`}>
                        <span> 중국</span>
                        {/* exchangeData가 null이 아닐 때만 해당 값을 출력 */}
                        {exchangeData && <div className={styles.price}>{exchangeAllList[0].price}</div>}
                        <div className={styles.priceContainer}>
                            <div className={styles.priceTitle}>현찰 살 때</div>
                            {exchangeData && (
                                <div className={styles.priceContent}>{exchangeAllList[0].cashBuyPrice}</div>
                            )}
                        </div>
                        <div className={styles.separator}></div>
                        <div className={styles.priceContainer}>
                            <div className={styles.priceTitle}>현찰 팔 때</div>
                            {exchangeData && (
                                <div className={styles.priceContent}>{exchangeAllList[0].cashSellPrice}</div>
                            )}
                        </div>
                    </div>
                    {/* <!-- Button --> */}
                    {/* <div className={styles.link}>
                        <a href="https://www.youtube.com/watch?v=wnJ6LuUFpMo">Listen</a>
                    </div> */}
                </div>

                {/* <!-- Separator --> */}
                <div className={styles.separator}></div>

                <div className={`${styles.chartBox} ${styles.chatBox2}`}>
                    {/* <!-- #02 --> */}
                    {/* <!-- Number--> */}
                    <div className={`${styles.chartNumber} ${styles.chartNumber2}`}>
                        <img src={JPN} alt="03" />
                    </div>
                    {/* <!-- Cover --> */}
                    <div className={`${styles.chartCover} ${styles.chartCover2}`}></div>
                    {/* <!-- Name --> */}
                    <div className={`${styles.chartName} ${styles.chartName2}`}>
                        <span> 일본</span>
                        {/* exchangeData가 null이 아닐 때만 해당 값을 출력 */}
                        {exchangeData && <div className={styles.price}>{exchangeAllList[1].price}</div>}
                        <div className={styles.priceContainer}>
                            <div className={styles.priceTitle}>현찰 살 때</div>
                            {exchangeData && (
                                <div className={styles.priceContent}>{exchangeAllList[1].cashBuyPrice}</div>
                            )}
                        </div>
                        <div className={styles.separator}></div>
                        <div className={styles.priceContainer}>
                            <div className={styles.priceTitle}>현찰 팔 때</div>
                            {exchangeData && (
                                <div className={styles.priceContent}>{exchangeAllList[1].cashSellPrice}</div>
                            )}
                        </div>
                    </div>
                    {/* <!-- Button --> */}
                    {/* <div className={styles.link}>
                        <a href="https://www.youtube.com/watch?v=wnJ6LuUFpMo">Listen</a>
                    </div> */}
                </div>
            </div>
        </div>
    );
};

export default ExExchange;
