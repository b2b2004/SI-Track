import React from 'react';
import ReactDOM from 'react-dom/client';
import { RecoilRoot } from 'recoil';
import Main from './Main';


const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
    <RecoilRoot>
        <Main />
    </RecoilRoot>
);
