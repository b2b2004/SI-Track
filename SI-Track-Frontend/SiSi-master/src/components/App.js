import Footer from "./Footer"
import Header from './Header';

export default function App({children}){
    return(
        <div id="wrap">
            <Header />
            <div>{children}</div>
            <Footer />
            </div>
    )
}