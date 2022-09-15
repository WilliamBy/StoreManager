/* util.js 工具模块 */
export function sleep(ms) {
    return new Promise(resolve => setTimeout(resolve, ms));
}