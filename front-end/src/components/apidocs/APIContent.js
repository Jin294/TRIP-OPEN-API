import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import styles from "./APIContent.module.css";
import basicHttp from "../../api/basicHttp";

const APIContent = (props) => {
  const [api_docs_id, setApiId] = useState("");
  // const { api_docs_id, setApiId } = useParams();
  const [apiContent, setApiContent] = useState([]);
  const [apiData, setApiData] = useState([]);
  const [nParam, setNParam] = useState(0);

  useEffect(() => {
    setApiId(props.data);
  }, [props]);

  useEffect(() => {
    const getApiDocsData = async (api_docs_id) => {
      try {
        const response = await basicHttp.get(`/docs/api/${api_docs_id}`);
        const responseData = response.data;
        // console.log("responseData", responseData.data);
        // console.log("api_docs_id", api_docs_id);

        if (responseData.data) {
          setApiContent(responseData.data);
          setApiData(responseData.data.api_data);
          console.log(responseData.data.api_data);
        }
      } catch (error) {
        console.log("Error fetching API data", error);
      }
    };

    if (api_docs_id) {
      getApiDocsData(api_docs_id);
    }
  }, [api_docs_id]);

  const isRequestTrueData = apiData.filter(
    (dataItem) => dataItem.is_request === true
  );
  const isRequestFalseData = apiData.filter(
    (dataItem) => dataItem.is_request === false
  );

  return (
    <div className={styles.contentBody}>
      <div>
        <h3>{apiContent.title}</h3>
        <div>{apiContent.content}</div>

        <table className={styles.firstTable}>
          <thead>
            <tr>
              <th></th>
              <th>설명</th>
            </tr>
          </thead>
          <tbody>
            <tr>
              <td>메서드</td>
              <td>{apiContent.method}</td>
            </tr>
            <tr>
              <td>호출 경로</td>
              <td>{apiContent.endpoint}</td>
            </tr>
            <tr>
              <td>응답 예시</td>
              <td>{apiContent.return_type}</td>
            </tr>
          </tbody>
        </table>

     
        <p>응답 예시</p>
        <pre id="json" className={styles.code}>
          {apiContent.return_example}
        </pre>

        {(isRequestTrueData.length>0 || apiContent.authorization === "1") && <h4>요청 메세지 명세</h4>}
        {(isRequestTrueData.length>0 || apiContent.authorization === "1") && <table className={styles.apiRequestDataTable}>
          <thead>
            <tr>
              <th>HTTP</th>
              <th>항목명</th>
              <th> 필수 </th>
              <th>타입</th>
              <th>설명</th>
            </tr>
          </thead>
          <tbody>
            {apiContent.authorization === "1" && (
              <tr>
                <td>Header</td>
                <td>authorization</td>
                <td></td>
                <td></td>
                <td>
                  사용자 OAuth2.0 인증을 통해 access-token을 받아야 합니다.{" "}
                  <br />
                  헤더 정보: Authorization Bearer {"${access-token}"}
                </td>
              </tr>
            )}
            {isRequestTrueData.map((dataItem, index) => (
              <tr key={index}>
                {index==0 && <td rowSpan={isRequestTrueData.length}>{dataItem.is_parameter? "Parameter" : "Body"}</td>}
                <td>{dataItem.title}</td>
                <td>{dataItem.is_essential ? " Y " : " N "}</td>
                <td>{dataItem.type}</td>
                <td>{dataItem.detail}</td>
              </tr>
            ))}
          </tbody>
        </table>}

        <h4>응답 메세지 명세</h4>
        <table className={styles.apiResponseDataTable}>
          <thead>
            <tr>
              <th>항목명</th>
              <th>타입</th>
              <th>설명</th>
            </tr>
          </thead>
          <tbody>
            {isRequestFalseData.map((dataItem, index) => (
              <tr key={index}>
                <td>{dataItem.title}</td>
                <td>{dataItem.type}</td>
                <td>{dataItem.detail}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default APIContent;
