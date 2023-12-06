import { Link, useParams } from 'react-router-dom';
import './ProductItem.css';
export default function ProductItem({post}){
    const {productCost, productDetail, productName, supplierCode, productImages} = post;
    return(
        <figure className='product'>
            <Link to={'/product/{productId}'}>
            <img src={productImages} alt={post.productName} />
                <figcaption>
                    <dl>
                        <dt>{productName}</dt>
                        <dd>{productDetail}</dd>
                    </dl>
                </figcaption>
                </Link>
        </figure>
    )
}