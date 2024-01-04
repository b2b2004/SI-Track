import { Link, useParams } from 'react-router-dom';
import './ProductItem.css';
export default function ProductItem({post}){
    const {productCost, productDetail, productName, supplierCode, productImageDtos, productId} = post;

    return(
        <figure className='product'>
            <Link to={`/product/${post.productId}`}>
            <img src={productImageDtos[0].imagePath + productImageDtos[0].saveName} alt={post.productName} />
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