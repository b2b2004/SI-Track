import { Link, useParams } from 'react-router-dom';
import './ProductItem.css';
export default function ProductItem({course}){

    return(
        <figure className='product'>
            <Link to={'/detail'}>
            <img src={course.photoUrl} alt={course.title} />
                <figcaption>
                    <dl>
                        <dt>{course.title}</dt>
                        <dd>{course.summary}</dd>
                    </dl>
                </figcaption>
                </Link>
        </figure>
    )
}