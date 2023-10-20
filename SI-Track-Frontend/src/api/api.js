import courses from './data.json';

export function getCouurses(keyword){
    if(!keyword) return courses;
    return filterByKeyword(courses,keyword);
}

function filterByKeyword(items,keyword){
    const lowerd = keyword.toLowerCase();
    return items.filter(({title}) => title.toLowerCase().includes(lowerd))
}

