import './Detail.css';
import img from '../assets/no01.png';
import detailimg1 from '../assets/no10.png';
import detailimg2 from '../assets/no11.png';
import detailimg3 from '../assets/no12.png';
import { Link } from "react-router-dom";

export default function Detail(){
    return(
        <div className="detailcontainer">
            <figure>
                <img src={img} alt="상품이미지"></img>
                <dl>
                    <dt>상품명</dt>
                    <dd>코드번호</dd>
                    <form action="post">
                       <label>구매수량
                       <input type="text"/></label>
                       <label>구매단가
                       <input type="text"disabled />
                       </label>
                       <label>구매금액
                       <input type="text" disabled />
                       </label>
                       <label>총금액
                       <input type="text" disabled/>
                       </label>
                    </form>
                    <button type="submit"><Link to='/pay'>결제하기</Link></button>
                    <button type="submit"><Link to='/cart'>장바구니</Link></button>
                    <button>견적서출력</button>
                </dl>
            </figure>
            <div>
                <button className="btn1"><a href='#detailimg'>상품상세정보</a></button>
                <button className="btn2"><a href='#productinfo'>제품안내</a></button>
                <button className="btn3"><a href='#buyinfo'>결제안내</a></button>
                <button className="btn4"><a href='#deliveryinfo'>배송 및 교환환불</a></button>
                <section>
                <figure id='detailimg'>
                    <img src={detailimg1} alt="상세이미지1"></img>
                    <img src={detailimg2} alt="상세이미지2"></img>
                    <img src={detailimg3} alt="상세이미지3"></img>
                </figure>
                <p id='productinfo'>제품안내<br></br> Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.</p>
                <p id='buyinfo'>결제안내<br></br> Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45 BC, making it over 2000 years old. Richard McClintock, a Latin professor at Hampden-Sydney College in Virginia, looked up one of the more obscure Latin words, consectetur, from a Lorem Ipsum passage, and going through the cites of the word in classical literature, discovered the undoubtable source. Lorem Ipsum comes from sections 1.10.32 and 1.10.33 of "de Finibus Bonorum et Malorum" (The Extremes of Good and Evil) by Cicero, written in 45 BC. This book is a treatise on the theory of ethics, very popular during the Renaissance. The first line of Lorem Ipsum, "Lorem ipsum dolor sit amet..", comes from a line in section 1.10.32.</p>
                <p id='deliveryinfo'>배송 및 교환환불 설명<br></br> It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout. The point of using Lorem Ipsum is that it has a more-or-less normal distribution of letters, as opposed to using 'Content here, content here', making it look like readable English. Many desktop publishing packages and web page editors now use Lorem Ipsum as their default model text, and a search for 'lorem ipsum' will uncover many web sites still in their infancy. Various versions have evolved over the years, sometimes by accident, sometimes on purpose (injected humour and the like).</p>
                </section>
            </div>
        </div>
    )
}