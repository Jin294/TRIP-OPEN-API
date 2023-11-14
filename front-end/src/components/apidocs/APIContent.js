import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import styles from './APIContent.module.css';
import basicHttp from '../../api/basicHttp';
//
import { Prism as SyntaxHighlighter } from 'react-syntax-highlighter';
import { vscDarkPlus } from 'react-syntax-highlighter/dist/esm/styles/prism';
import { vs } from 'react-syntax-highlighter/dist/esm/styles/prism';

const APIContent = (props) => {
    const [api_docs_id, setApiId] = useState('');
    // const { api_docs_id, setApiId } = useParams();
    const [apiContent, setApiContent] = useState([]);
    const [apiData, setApiData] = useState([]);
    const [nParam, setNParam] = useState(0);

    useEffect(() => {
        setApiId(props.data);
        console.log(props.data);
    }, [props]);

    useEffect(() => {
        const getApiDocsData = async (api_docs_id) => {
            try {
                const response = await basicHttp.get(`/docs/data/api/info/${api_docs_id}`);
                const responseData = response.data;
                // console.log("responseData", responseData.data);
                // console.log("api_docs_id", api_docs_id);

                if (responseData.data) {
                    setApiContent(responseData.data);
                    setApiData(responseData.data.variable_info);
                    console.log(responseData.data.variable_info);
                }
            } catch (error) {
                console.log('Error fetching API data', error);
            }
        };

        if (api_docs_id) {
            getApiDocsData(api_docs_id);
        }
    }, [api_docs_id]);

    const isRequestTrueData = [];
    const isRequestFalseData = [];

    apiData.forEach((item) => {
        if (item.is_request) {
            isRequestTrueData.push(item);
        } else {
            isRequestFalseData.push(item);
        }
    });

    // const isRequestTrueData = apiData.filter(
    //   (dataItem) => dataItem.is_request === true
    // );
    // const isRequestFalseData = apiData.filter(
    //   (dataItem) => dataItem.is_request === false
    // );

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

                <h4>응답 예시</h4>
                <pre id="json" className={styles.code}>
                    <SyntaxHighlighter language="json" style={vs}>
                        {apiContent.return_example}
                    </SyntaxHighlighter>
                </pre>

                {isRequestTrueData.length > 0 && <h4>요청 메세지 명세</h4>}
                {isRequestTrueData.length > 0 && (
                    <table className={styles.apiRequestDataTable}>
                        <thead>
                            <tr>
                                <th>HTTP</th>
                                <th>항목명</th>
                                <th> 필수 </th>
                                <th>파라미터</th>
                                <th>타입</th>
                                <th>설명</th>
                            </tr>
                        </thead>
                        <tbody>
                            {isRequestTrueData.map((dataItem, index) => (
                                <tr key={index}>
                                    {index == 0 && (
                                        <td rowSpan={isRequestTrueData.length}>
                                            {dataItem.is_parameter ? 'Parameter' : 'Body'}
                                        </td>
                                    )}
                                    <td>{dataItem.title}</td>
                                    <td>{dataItem.is_essential ? ' Y ' : ' N '}</td>
                                    <td>{dataItem.is_parameter ? ' Y ' : ' N '}</td>
                                    <td>{dataItem.type}</td>
                                    <td>{dataItem.detail}</td>
                                </tr>
                            ))}
                        </tbody>
                    </table>
                )}

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
