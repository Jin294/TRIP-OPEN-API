import React, { useEffect, useState } from "react";
import styles from "./SideBar.module.css";
import { NavLink } from "react-router-dom";
import basicHttp from "../../api/basicHttp";
import { useParams } from "react-router-dom";

const SideBar = ({onSetId}) => {
  const { tab } = useParams();
  const [tabsData, setTabsData] = useState([]);
  const [selectedTab, setSelectedTab] = useState("금융 더미 데이터");
  const [selectedSub, setSelectedSub] = useState(3);
  const setId = (data) => {
    onSetId(data)
  }

  useEffect(() => {
    if(tab === 'exchange') handleTabClick("환율 정보");
    else if(tab === 'investment') handleTabClick("투자 자산 분석");
    else if(tab === 'consumption') handleTabClick("소비 내역 분석");
    else handleTabClick("금융 더미 데이터");
  }, [tab]);

  const handleTabClick = (tab) => {
    setSelectedTab(tab);
    
    if(tab === "환율 정보") handleSubClick(5);
    else if(tab === "투자 자산 분석") handleSubClick(13);
    else if(tab === "소비 내역 분석") handleSubClick(19);
    else handleSubClick(3);
  };

  const handleSubClick = (sub) => {
    setSelectedSub(sub);
    setId(sub);
  }

  useEffect(() => {
    const getApiDocsList = async () => {
      try {
        const response = await basicHttp.get("/docs/api");
        const responseData = response.data;
        // console.log("responseData", responseData);

        if (responseData.data) {
          const groupedTabs = [
            {
              title: "금융 더미 데이터",
              url: "/apidock/financialdata",
              subTabs: responseData.data.slice(0, 2),
            },
            {
              title: "환율 정보",
              url: "/apidock/exchange",
              subTabs: responseData.data.slice(2, 10),
            },
            {
              title: "투자 자산 분석",
              url: "/apidock/investment",
              subTabs: responseData.data.slice(10, 16),
            },
            {
              title: "소비 내역 분석",
              url: "/apidock/consumption",
              subTabs: responseData.data.slice(16, 17),
            },
          ];
          setTabsData(groupedTabs);
        }
      } catch (error) {
        console.log("Error fetching API data", error);
      }
    };

    getApiDocsList();
  }, []);

  return (
    <div className={styles.sidebar}>
      <aside>
        <ul>
          {tabsData.map((group) => (
            <li key={group.title}>
              <h3
                className={selectedTab === group.title ? styles.selectedTab : ""}
              >
                <NavLink
                  to={group.url}
                  onClick={() => handleTabClick(group.title)}
                >
                  {group.title}
                </NavLink>
              </h3>
              <ul>
               {
                selectedTab === group.title &&
                 group.subTabs.map((tab) => (
                   <li key={tab.api_docs_id}>
                    <div className={selectedSub === tab.api_docs_id? styles.selected: styles.noSelected} 
                      onClick={() => handleSubClick(tab.api_docs_id)}>
                      {tab.title}
                    </div>
                   {/* <NavLink
                   to={`/apidock/${tab.api_docs_id}`}
                   className={selectedSub==tab.api_docs_id? styles.selected: styles.noSelected} 
                   onClick={() => handleSubClick(tab.api_docs_id)}
                    >
                    {tab.title}
                    </NavLink> */}
                    </li>
                  ))}
              </ul>
            </li>
          ))}
        </ul>
      </aside>
    </div>
  );
};

export default SideBar;
