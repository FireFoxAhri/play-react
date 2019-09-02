package filters

import java.net.URLDecoder

import akka.stream.Materializer
import javax.inject._
import play.api.Logging
import play.api.mvc._

import scala.concurrent.{ExecutionContext, Future}

/**
 * This is a simple filter that adds a header to all requests. It's
 * added to the application's list of filters by the
 * [[Filters]] class.
 *
 * @param mat This object is needed to handle streaming of requests
 *            and responses.
 * @param ec  This class is needed to execute code asynchronously.
 *            It is used below by the `map` method.
 */
@Singleton
class LoggingFilter @Inject()(implicit val mat: Materializer, ec: ExecutionContext) extends Filter with Logging {
  def apply(nextFilter: RequestHeader => Future[Result])(requestHeader: RequestHeader): Future[Result] = {
    val startTime = System.currentTimeMillis
    val newReq = requestHeader

    nextFilter(newReq).map { result =>
      val endTime = System.currentTimeMillis
      val requestTime = endTime - startTime
      logger.info(s"${requestHeader.method} ${URLDecoder.decode(requestHeader.uri, "UTF-8")} took $requestTime ms and returned ${result.header.status}")
      result
    }
  }
}
