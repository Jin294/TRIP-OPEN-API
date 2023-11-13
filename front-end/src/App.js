import "./App.css";
import { Routes, Route, useNavigate, useLocation } from "react-router-dom";

// 메인페이지
import Home from "./components/main/Home";
// 회원가입
import Signup from "./components/signup/Signup";
// 로그인
import Login from "./components/login/Login";
// 마이페이지
import Mypage from "./components/mypage/Mypage";
// 서비스 소개
import ServiceInfo from "./components/serviceinfo/ServiceInfo";
import APIDocs from "./components/apidocs/APIDocs";
import ElasticInfo from "./components/elastic/ElasticInfo";
// API문서
// 예시 페이지
import ExExchange from "./components/examplepage/ExExchange";
import ExCard from "./components/examplepage/ExCard";
import ExCardContent from "./components/examplepage/ExCardContent";

import { useEffect, useRef } from "react";

function App() {
  const navigate = useNavigate();
  const location = useLocation();

  // routes 배열에 포함된 원소가 아니라면 강제로 Home으로 리다이렉트
  const routes = useRef([
    "/",
    "/signup",
    "/login",
    "/mypage",
    "/serviceinfo",
    "/apidock",
    "/exexchange",
    "/excard",
    "/excardcontent",
  ]);

  useEffect(() => {
    if (!routes.current.some((path) => location.pathname.startsWith(path))) {
      if (location.pathname !== "/") {
        navigate("/");
      }
    }
  }, [location.pathname, navigate]);

  return (
    <div className="App">
      <Routes>
        <Route path="" element={<Home />} />
        <Route path="/signup" element={<Signup />}></Route>
        <Route path="/login" element={<Login />}></Route>
        <Route path="/mypage" element={<Mypage />}></Route>
        {/* <Route path="/serviceinfo" element={<ServiceInfo />}></Route> */}
        <Route path="/elasticinfo" element={<ElasticInfo />} />
        <Route path="/apidock" element={<APIDocs />} />
        <Route path="/apidock/:tab" element={<APIDocs />}></Route>
        <Route path="/exexchange" element={<ExExchange />}></Route>
        <Route path="/excard" element={<ExCard />}></Route>
        <Route path="/excardcontent" element={<ExCardContent />}></Route>
        <Route path="/*" element={<Home />} />
      </Routes>
    </div>
  );
}

export default App;
