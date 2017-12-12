/**
 * 此包下用于放置更新流程中的关键更新节点实现类，如：
 * <ol>
 *     <li>
 *         {@link org.lzh.framework.updatepluginlib.flow.Launcher}:
 *         启动检查更新api接口与文件下载网络任务的入口
 *     </li>
 *     <li>
 *         {@link org.lzh.framework.updatepluginlib.flow.DefaultCheckCallback}:
 *         衔接检查更新api网络任务。并进行后续流程派发
 *     </li>
 *     <li>
 *         {@link org.lzh.framework.updatepluginlib.flow.DefaultDownloadCallback}:
 *         衔接文件下载网络任务。并进行后续流程派发
 *     </li>
 *     <li>
 *         {@link org.lzh.framework.updatepluginlib.flow.CallbackDelegate}:
 *         统一触发更新流程中的各个回调方法。并添加日志打印输出功能。
 *     </li>
 * </ol>
 */
package org.lzh.framework.updatepluginlib.flow;