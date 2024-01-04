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
import Register from "./page/Register";
import Pay from "./page/Pay";
import Paycomplete from "./page/Paycomplete";
import Cart from "./page/Cart";
import Userlist from "./page/Userlist";
import Orderlist from "./page/Orderlist";
import Supplierlist from "./page/Supplierlist";
import Categorylist from "./page/Categorylist";

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
            <Route path="/product/:id" element={<Detail />} />
            <Route path="/success" element={<Success />} />
            <Route path="/mypage" element={<Mypage />} />
            <Route path="/change" element={<Change />} />
            <Route path="/register" element={<Register />} />
            <Route path="/pay" element={<Pay />} />
            <Route path="/Paycomplete" element={<Paycomplete />} />
            <Route path="/cart" element={<Cart />} />
            <Route path="/userlist" element={<Userlist />} />
            <Route path="/orderlist" element={<Orderlist/>} />
            <Route path="/supplierlist" element={<Supplierlist/>} />
            <Route path="/categorylist" element={<Categorylist/>} />
            </Routes>
        </App>
        </BrowserRouter>
    )
}