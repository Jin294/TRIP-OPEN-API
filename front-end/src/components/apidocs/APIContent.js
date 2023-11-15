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
                <h2>{apiContent.title}</h2>
                <div className={styles.contentText}>{apiContent.content}</div>
                <h3>1. 기본 설명</h3>

                <table className={styles.firstTable}>
                    <thead>
                        <tr>
                            <th></th>
                            <th>설명</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td>헤더</td>
                            <td>
                                저희 서비스에서는 API 요청을 인증하기 위해 Bearer 타입의 토큰을 사용합니다. Bearer
                                토큰은 로그인 후 우측상단의 '내 API 토큰' 버튼을 눌러 확인 하실 수 있습니다. 토큰은 API
                                요청의 헤더의 Authorization 필드에 담아 보내주세요, 이를 통해 사용자를 식별합니다.
                            </td>
                        </tr>
                        <tr>
                            <td>메서드</td>
                            <td>{apiContent.method}</td>
                        </tr>
                        <tr>
                            <td>호출경로</td>
                            <td>{apiContent.endpoint}</td>
                        </tr>
                        <tr>
                            <td>응답형식</td>
                            <td>{apiContent.return_type}</td>
                        </tr>
                    </tbody>
                </table>

                <h3>2. 요청</h3>

                {isRequestTrueData.length > 0 && (
                    <div>
                        <h4>* 쿼리 파라미터</h4>
                    </div>
                )}
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
                                    <td style={{ color: dataItem.is_essential ? 'red' : 'black' }}>
                                        {dataItem.is_essential ? ' Y ' : ' N '}
                                    </td>
                                    <td>{dataItem.is_parameter ? ' Y ' : ' N '}</td>
                                    <td>{dataItem.type}</td>
                                    <td>{dataItem.detail}</td>
                                </tr>
                            ))}
                        </tbody>
                    </table>
                )}
                <div>
                    <h3>3. 응답</h3>
                    <h4>* 쿼리 파라미터</h4>
                </div>
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

                <h3>4. 예제</h3>
                <h4>* 요청</h4>
                <pre id="bash" className={styles.code}>
                    <SyntaxHighlighter language="bash" style={vs}>
                        https://k9b205.p.ssafy.io/
                    </SyntaxHighlighter>
                </pre>
                <h4>* 응답</h4>
                <pre id="json" className={styles.code}>
                    <SyntaxHighlighter language="json" style={vs}>
                        {apiContent.return_example}
                    </SyntaxHighlighter>
                </pre>

                <h3>5. 테스트(미리 보기)</h3>
                <h4>* 요청 변수(Request Parameter)</h4>
                <h4>쿼리 파라미터</h4>
                {isRequestTrueData.length > 0 && (
                    <table className={styles.apiRequestDataTable}>
                        <thead>
                            <tr>
                                <th>HTTP</th>
                                <th>항목명</th>
                                <th> 필수 </th>
                                <th>파라미터</th>
                                <th>타입</th>
                                <th>샘플 데이터</th>
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
                                    <td style={{ color: dataItem.is_essential ? 'red' : 'black' }}>
                                        {dataItem.is_essential ? ' Y ' : ' N '}
                                    </td>
                                    <td>{dataItem.is_parameter ? ' Y ' : ' N '}</td>
                                    <td>{dataItem.type}</td>
                                    <td>
                                        <input></input>
                                    </td>
                                </tr>
                            ))}
                        </tbody>
                    </table>
                )}
                <h4>* 응답</h4>
                <pre id="json" className={styles.code}>
                    <SyntaxHighlighter language="json" style={vs}>
                        {apiContent.return_example}
                    </SyntaxHighlighter>
                </pre>
            </div>
        </div>
    );
};

export default APIContent;
