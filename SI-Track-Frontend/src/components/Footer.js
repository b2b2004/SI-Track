import './Footer.css'
import { BsInstagram,BsFacebook } from 'react-icons/bs'
import logo from '../assets/logo.png';

export default function Footer(){
    return(
        <footer>
 <div className="footerwrap">
            {/* <ul className='firstul'>
                <li>
            
                </li>
                <li>
                <ul className='ligroup'>
                <li>Follow us</li>
                <li><BsInstagram /></li>
                <li><BsFacebook /></li>
                </ul>
                </li>
            </ul> */}
            <ul className='secondul'>
                <li>회사소개</li>
                <li>이용약관</li>
                <li>개인정보처리방침</li>
                <li>이용안내</li>
                <li>고객센터</li>
            </ul>
            <ul className='thirdul'>
                <li>
                    <dl>
                        <dt>CUSTOMER CENTER</dt>
                        <dt>Tel. 1234-5678</dt>
                        <dd>고객센터 운영시간</dd>
                        <dd>평일 오전 00:00 - 오후18:00
점심시간 오후 12:00 - 오후 1:00
토/일/공휴일 휴무</dd>
                        <dd>영업시간 이외에는 우측 하단에 '문의/상담'에 문의하시면
담당자 확인 후 빠른 답변 도와드리겠습니다.</dd>
                    </dl>
                </li>
                                <li>
                    <dl>
                        <dt>BANK</dt>
                        <dd>KB국민은행12345678910<br></br>
(주)Si-Track</dd>
                        <dt>RETURN / EXCHANGE</dt>
                        <dd>(00000) 서울특별시 서대문구 123-4567</dd>
                        <dd>자세한 교환 · 반품절차 안내는 문의란 및 공지사항을
참고해주세요</dd>
                    </dl>
                </li>
                <li>
                    <dl>
                        <dt>COMPANY</dt>
                        <dd>상호명 : (주)주식회사 Si-Track : 홍길동
본사 : 서울특별시 서대문구 123-4567</dd>
                        <dd>사업자등록번호 : 000-00-0000 [사업자번호조회]<br></br>
통신판매업신고번호 : 제0000-와부조안-0000호<br></br>
개인정보보호책임자 : 정경진</dd>
                        <dd>TEL : 1233-5678FAX : 02-9876-5432<br></br>
                            Email / 제휴&제안 : hongildong@hongildong</dd>
                        <dd>COPYRIGHT ⓒ 2023 (주)주식회사 Si-Track | Si All Rights Reserved.</dd>
                    </dl>
                </li>
            </ul>
        </div>
        </footer>
    )
}