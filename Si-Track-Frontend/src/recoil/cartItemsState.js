import { atom } from 'recoil';
import { recoilPersist } from 'recoil-persist';
const { persistAtom } = recoilPersist();

export const cartItemsState = atom({
    key: 'cartItemsState',
    default: [],
    effects_UNSTABLE: [persistAtom]
});