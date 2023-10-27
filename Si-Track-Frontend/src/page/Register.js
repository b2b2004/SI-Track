import { useState } from 'react';
import './Register.css';
export default function Register(){
  const [img,setImg] = useState([]);

  const saveImg = (e) =>{
    const fileList = e.target.files;
    const imgUrlList = [...img];
    for(let i = 0; i<fileList.length; i++){
      const ImgUrl = URL.createObjectURL(fileList[i]);
      imgUrlList.push(ImgUrl);
    }
    if(imgUrlList.length>4){
      alert('사진의 최대 개수를 초과하였습니다.');
      return false;
      imgUrlList = imgUrlList.slice(0,4);
    }
        setImg(imgUrlList); 
        }
    return(
        <div className='register-container'>
           <form>
        <fieldset>
          <ul>
        <li><label htmlFor="p_name">상품명</label>
        <input type="text" name="p_name" id="p_name" placeholder='상품명을 입력해주세요.'/></li>
        <li><label htmlFor="u_email">재고량</label>
        <input type="text" name="p_num" id="p_num" placeholder='재고량을 입력해주세요'/></li>
        <li><label htmlFor="p_buy">구매가</label>
        <input type="text" name="p_buy" id="p_buy" placeholder='구매가를 입력해주세요.'/></li>
        <li><label htmlFor="p_sell">판매가</label>
        <input type='text' name='p_sell' id='p_sell'placeholder='판매가를 입력해주세요.'/></li>
        <li><label htmlFor='p_info'>상품설명</label>
        <textarea name="p_info" id="p_info" cols="30" rows="10" placeholder='상품설명을 입력해주세요.'></textarea></li>
        <li><label htmlFor='p_img'>상품이미지 <input accept='image/*' multiple type='file' onChange={saveImg} /></label>
        <ul className='imgcont'>
        {img.map((image, id) => (
          <li>
            <img src={image} alt='이미지'key={id} />
            <button onClick={(id)=>{
              setImg(img.filter((_,index)=>index !== id));
            }}>삭제</button>
        </li>
        ))}
        </ul>
            </li>
          </ul>
            <p><input type="submit" value="등록"/></p>
            <p><input type="submit" value="취소"/></p>
          </fieldset>
          </form>
            </div>
        )
}
