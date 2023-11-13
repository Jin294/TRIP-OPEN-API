import React, { useEffect, useState } from "react";
import styles from "./SideBar.module.css";
import { NavLink } from "react-router-dom";
import basicHttp from "../../api/basicHttp";
import { useParams } from "react-router-dom";

const SideBar = ({onSetId}) => {
  const { tab } = useParams();
  const [tabsData, setTabsData] = useState([]);
  const [selectedTab, setSelectedTab] = useState("숙소 API");
  const [selectedSub, setSelectedSub] = useState(3);
  const setId = (data) => {
    onSetId(data)
  }

  useEffect(() => {
    if(tab === 'exchange') handleTabClick("음식점 API");
    else if(tab === 'investment') handleTabClick("검색 API");
    else handleTabClick("숙소 API");
  }, [tab]);

  const handleTabClick = (tab) => {
    setSelectedTab(tab);
    
    if(tab === "음식점 API") handleSubClick(5);
    else if(tab === "검색 API") handleSubClick(13);
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
              title: "숙소 API",
              url: "/apidock/accommodation",
              subTabs: responseData.data.slice(0, 2),
            },
            {
              title: "음식점 API",
              url: "/apidock/restaurant",
              subTabs: responseData.data.slice(2, 10),
            },
            {
              title: "검색 API",
              url: "/apidock/search",
              subTabs: responseData.data.slice(10, 16),
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
