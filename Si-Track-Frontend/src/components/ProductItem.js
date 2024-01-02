import { Link, useParams } from 'react-router-dom';
import './ProductItem.css';
export default function ProductItem({post}){
<<<<<<< Updated upstream
    const {productCost, productDetail, productName, supplierCode, productImages} = post;
    return(
        <figure className='product'>
            <Link to={'/product/{productId}'}>
            <img src={productImages} alt={post.productName} />
=======
    const {productCost, productDetail, productName, supplierCode, productImageDtos, productId} = post;

    console.log(post);
    return(
        <figure className='product'>
            <Link to={`/product/${post.productId}`}>
            <img src={productImageDtos.imagePath} alt={post.productName} />
>>>>>>> Stashed changes
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