import {BrowserRouter, Route, Routes} from "react-router-dom";
import App from "./components/App";
import Home from "./page/Home";
import NotFind from "./page/NotFind";
import Login from "./page/Login";
import Signup from "./page/Signup";
import FindId from "./page/FindId";
import Detail from "./page/Detail";
import Success from "./page/Success";
import Mypage from "./page/Mypage";
import Change from "./page/Change";

export default function Main(){
    return(
        <BrowserRouter>
        <App>
            <Routes>
            <Route path="/" element={<Home />} />
            <Route path='*' element={<NotFind />} />
            <Route path='/login' element={<Login />} />
            <Route path='/signup' element={<Signup />} />
            <Route path='/findid' element={<FindId />} />
            <Route path="/detail" element={<Detail />} />
            <Route path="/success" element={<Success />} />
            <Route path="/mypage" element={<Mypage />} />
            <Route path="/change" element={<Change />} />
            </Routes>
        </App>
        </BrowserRouter>
    )
}