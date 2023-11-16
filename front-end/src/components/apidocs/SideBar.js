import React, { useEffect, useState } from "react";
import styles from "./SideBar.module.css";
import { NavLink } from "react-router-dom";
import basicHttp from "../../api/basicHttp";
import { useParams } from "react-router-dom";

const SideBar = ({ onSetId }) => {
  const { tab } = useParams();
  const [tabsData, setTabsData] = useState([]);
  const [selectedTab, setSelectedTab] = useState("숙소 API");
  const [selectedSub, setSelectedSub] = useState(1);
  const [searchnum, setSearchNum] = useState(0);
  const [searchapi, setSearchApi] = useState(0);

  const setId = (data) => {
    onSetId(data);
  };

  useEffect(() => {
    if (tab === "restaurant") handleTabClick("음식점 API");
    else if (tab === "search") handleTabClick("검색 API");
    else handleTabClick("숙소 API");
  }, [tab]);

  const handleTabClick = (tab) => {
    setSelectedTab(tab);
    console.log("searchNum", searchnum);
    if (tab === "음식점 API") handleSubClick(3, 3);
    else if (tab === "검색 API") handleSubClick(searchnum, searchapi);
    else handleSubClick(1, 1);
  };

  const handleSubClick = (sub, api_num) => {
    setSelectedSub(sub);
    setId(api_num);
  };

  useEffect(() => {
    const getApiDocsList = async () => {
      try {
        const response = await basicHttp.get(`/docs/data/apilist`);
        const responseData = response.data;
        // console.log("responseData", responseData);
        // console.log(responseData.data.length);

        if (responseData.data) {
          const groupedTabs = [
            {
              title: "숙소 API",
              url: "/apidocs/accommodation",
              subTabs: responseData.data.slice(0, 2),
            },
            {
              title: "음식점 API",
              url: "/apidocs/restaurant",
              subTabs: responseData.data.slice(2, 4),
            },
            {
              title: "검색 API",
              url: "/apidocs/search",
              subTabs: responseData.data
                .slice(4, responseData.data.length)
                .sort((a, b) => a.apiFrontId - b.apiFrontId),
            },
          ];
          setTabsData(groupedTabs);
          setSearchNum(groupedTabs[2].subTabs[0].apiFrontId);
          setSearchApi(groupedTabs[2].subTabs[0].api_data_id);
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
                className={
                  selectedTab === group.title ? styles.selectedTab : ""
                }
              >
                <NavLink
                  to={group.url}
                  onClick={() => handleTabClick(group.title)}
                >
                  <p
                    className={
                      selectedTab === group.title ? styles.selectedTabText : ""
                    }
                  >
                    {group.title}
                  </p>
                </NavLink>
              </h3>
              <ul>
                {selectedTab === group.title &&
                  group.subTabs.map((tab) => (
                    <li key={tab.apiFrontId}>
                      <div
                        className={
                          selectedSub === tab.apiFrontId
                            ? styles.selected
                            : styles.noSelected
                        }
                        onClick={() =>
                          handleSubClick(tab.apiFrontId, tab.api_data_id)
                        }
                      >
                        {tab.title}
                      </div>
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
