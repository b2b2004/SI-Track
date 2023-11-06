import './Change.css'
export default function Change(){
    return(
        <div className="changecontainer">
            <h1>내정보</h1>
            <form>
            <table>
                <tbody>
                <tr>
                        <th>아이디</th>
                        <td>kmkm35</td>
                    </tr>
                    <tr>
                        <th>이름</th>
                        <td><input id='name' defaultValue='정경진' disabled='true'></input></td>
                        <td><button onClick={(e)=>{
                            const namenum = 1;
                            e.preventDefault(); 
                            if(namenum===1){
                                document.querySelector('#name').disabled=false;
                            }
                            else if(namenum!==1){
                                document.querySelector('#name').disabled=true;
                            }
                        }}>이름 변경</button></td>
                    </tr>

                    <tr>
                        <th>비밀번호</th>
                        <td><input id='password' type='password' defaultValue='*********' disabled></input></td>
                        <td><button onClick={(e)=>{
                            const passnum = 1;
                            e.preventDefault(); 
                            if(passnum===1){
                                document.querySelector('#password').disabled=false;
                            }
                            else if(passnum!==1){
                                document.querySelector('#password').disabled=true;
                            }
                        }}>비밀번호 변경</button></td>
                    </tr>
                    <tr>
                        <th>이메일</th>
                        <td><input id='email' defaultValue='abc@naver.com'disabled></input></td>
                        <td><button onClick={(e)=>{
                            const emailnum = 1;
                            e.preventDefault(); 
                            if(emailnum===1){
                              document.querySelector('#email').disabled=false;
                                }
                                else if(emailnum!==1){
                                document.querySelector('#email').disabled=true;
                                }
                        }}>이메일 수정</button></td>
                    </tr>
                    <tr>
                        <th>전화번호</th>
                        <td><input id='phonenum' defaultValue='010-1234-5678' disabled></input></td>
                        <td><button onClick={(e)=>{
                           const phone = 1;
                           e.preventDefault(); 
                           if(phone===1){
                             document.querySelector('#phonenum').disabled=false;
                               }
                               else if(phone!==1){
                               document.querySelector('#phonenum').disabled=true;
                               }
                        }}>전화번호 변경</button></td>
                    </tr>
                </tbody>
            </table>
            </form>
        </div>
    )
}