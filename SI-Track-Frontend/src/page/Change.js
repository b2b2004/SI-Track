import './Change.css'
export default function Change(){
    return(
        <div className="changecontainer">
            <h1>내정보</h1>
            <form method="post">
            <table>
                <tbody>
                <tr>
                        <th>아이디</th>
                        <td>kmkm35</td>
                    </tr>
                    <tr>
                        <th>이름</th>
                        <td>정경진</td>
                        <td><button>이름 변경</button></td>
                    </tr>

                    <tr>
                        <th>비밀번호</th>
                        <td>********</td>
                        <td><button>비밀번호 변경</button></td>
                    </tr>
                    <tr>
                        <th>이메일</th>
                        <td>abc@naver.com</td>
                        <td><button>이메일 수정</button></td>
                    </tr>
                    <tr>
                        <th>전화번호</th>
                        <td>010-1234-5678</td>
                        <td><button>전화번호 변경</button></td>
                    </tr>
                </tbody>
            </table>
            </form>
        </div>
    )
}