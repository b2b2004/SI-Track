import { useRef, useState } from 'react';
import './Register.css';
export default function Register(){
    const [imgFile,setImgFile] = useState('');
    const imgRef = useRef();

    const saveImgFile = (e) =>{
        const file = imgRef.current.files[0];
        const reader = new FileReader();
        reader.readAsDataURL(file);
        reader.onloadend = () =>{
            setImgFile(reader.result);
        }
    }
    return(
        <div className='register-container'>
           <form action="#" method="post">
        <fieldset>
          <ul>
        <li><label for="p_name">상품명</label>
        <input type="text" name="p_name" id="p_name" placeholder='상품명을 입력해주세요.'/></li>
        <li><label for="u_email">재고량</label>
        <input type="text" name="p_num" id="p_num" placeholder='재고량을 입력해주세요'/></li>
        <li><label for="p_buy">구매가</label>
        <input type="text" name="p_buy" id="p_buy" placeholder='구매가를 입력해주세요.'/></li>
        <li><label for="p_sell">판매가</label>
        <input type='text' name='p_sell' id='p_sell'placeholder='판매가를 입력해주세요.'/></li>
        <li><label for='p_info'>상품설명</label>
        <textarea name="p_info" id="p_info" cols="30" rows="10" placeholder='상품설명을 입력해주세요.'></textarea></li>
        <li><label for='p_img'>상품이미지 <input accept='image/*' multiple type='file' onChange={saveImgFile} ref={imgRef} /></label>
    <div className='imgcont'>
    <img src={imgFile ? imgFile : ''} alt='이미지' />
    </div>
        </li>
      </ul>
        <p><input type="submit" value="등록"/></p>
        <p><input type="submit" value="취소"/></p>
      </fieldset>
      </form>
        </div>
    )
}
