import { useDaumPostcodePopup } from "react-daum-postcode";

export default function DaumPost(props){
    const scriptUrl =
    'https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js';
    const open = useDaumPostcodePopup(scriptUrl);
    const handleComplete = (data) => {
      let fullAddress = data.address;
      let extraAddress = ''; 
      let localAddress = data.sido + ' ' + data.sigungu;
      if (data.addressType === 'R') { 
        if (data.bname !== '') {
          extraAddress += data.bname; 
        }
        if (data.buildingName !== '') {
          extraAddress += (extraAddress !== '' ? `, ${data.buildingName}` : data.buildingName);
        }
        fullAddress = fullAddress.replace(localAddress, '');
        props.setAddressObj({
          areaAddress: localAddress,
          townAddress: fullAddress += (extraAddress !== '' ? `(${extraAddress})` : '')
        });
      }
    }
    const handleClick = () => {
        open({onComplete: handleComplete});
    }
    return <button type="button" onClick={handleClick}>주소찾기</button>
   }
