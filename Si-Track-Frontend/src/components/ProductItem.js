import { Link, useParams } from 'react-router-dom';
import './ProductItem.css';
export default function ProductItem({post}){
    const {productCost, productDetail, productName, supplierCode, productImages, productId} = post;
  main
    return(
        <figure className='product'>
            <Link to={`/product/${post.productId}`}>
            <img src={productImages} alt={post.productName} />

    const {productCost, productDetail, productName, supplierCode, productImageDtos, productId} = post;

    console.log(post);
    return(
        <figure className='product'>
            <Link to={`/product/${post.productId}`}>
            <img src={productImageDtos.imagePath} alt={post.productName} />
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