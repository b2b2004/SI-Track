import ProductItem from "../components/ProductItem";
import './Productlist.css';
import { getCouurses } from "../api/api";

export default function Productlist(){
    const courses = getCouurses();
    return(
        <div id="product">
        <h1>title</h1>
        <form>
        <input type="text" placeholder="검색어를 입력해주세요"></input>
        <button type="submit">검색</button>
        </form>
            <div className="container">
            <p>총 
                {courses.length}
                개가 검색되었습니다.</p>
            <div>
                {courses.map((course)=>(<ProductItem key={course.id} course={course}/>))}
            </div>
            </div>
        </div>
    )
}